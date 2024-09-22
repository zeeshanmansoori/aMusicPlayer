package com.zee.amusicplayer.presentation.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaBrowser
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.SessionToken
import com.zee.amusicplayer.utils.SortBy
import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.domain.model.toSong
import com.zee.amusicplayer.domain.useCase.album.AlbumUseCase
import com.zee.amusicplayer.domain.useCase.artist.ArtistsUseCase
import com.zee.amusicplayer.domain.useCase.playlist.PlayListUseCase
import com.zee.amusicplayer.service.MusicService
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MediaItemHelper
import com.zee.amusicplayer.utils.fixedItemIndex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application),
    MediaBrowser.Listener {

    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible = _isSearchVisible.asStateFlow()

    private val _filterKey = MutableStateFlow("")
    val filterKey = _filterKey.asStateFlow()

    private val executor = ContextCompat.getMainExecutor(application)

    @SuppressLint("UnsafeOptInUsageError")
    private val browserFuture = MediaBrowser.Builder(
        application,
        SessionToken(application, ComponentName(application, MusicService::class.java))
    ).setListener(this).buildAsync()

    private val browser: MediaBrowser?
        get() = if (browserFuture.isDone && !browserFuture.isCancelled) browserFuture.get() else null

    private val _songsState = MutableStateFlow(SongsState(isLoading = true))
    private val _sortBy = MutableStateFlow<SortBy>(SortBy.Name)

    val songsState =
        combine(_songsState, _sortBy, _filterKey) { songsState, sortBy, filterKey ->
            val list = songsState.songs
            var songs = sortBy.sortList(list)

            if (filterKey.isNotBlank()) {
                songs = songs.filter {
                    it.title.contains(filterKey, true)
                }
            }

            songsState.copy(songs = songs)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SongsState(isLoading = true))

    private val _playerState = MutableStateFlow(PlayerState.NotPlaying)
    val playerState = _playerState.asStateFlow()

    private var progressTrackingJob: Job? = null

    val sortByE = _sortBy.asStateFlow()


    val albumUseCase by lazy { AlbumUseCase(songsState, viewModelScope) }
    val artistsUseCase by lazy { ArtistsUseCase(songsState, viewModelScope) }
    val playListUseCase by lazy { PlayListUseCase( viewModelScope) }

    init {
        browserFuture.addListener({
            val browser = this.browser ?: return@addListener
            browser.subscribe(MediaItemHelper.Root.mediaId, null)
            setController()
        }, executor)

    }

    fun triggerFetchMusicWorker() {
        _songsState.value = _songsState.value.copy(isLoading = true)
        MusicService.scheduleFetchTask(getApplication())
    }

    fun onFilterKeyChanged(key: String) {
        _filterKey.value = key
    }

    fun onSearchBtnClicked() {
        _isSearchVisible.value = !_isSearchVisible.value
    }

    private fun getChildren(rootId: String) {

        val browser = this.browser ?: return

        val childrenFuture = browser.getChildren(
            rootId,
            /* page= */ 0,
            /* pageSize= */ Int.MAX_VALUE,
            /* params= */ null
        )

        childrenFuture.addListener(
            {
                val result = childrenFuture.get()!!
                val children = result.value!!
                // setting itemIndex to track the position of mediaItem within player
                _songsState.value = SongsState(isLoading = false, children.map { it.toSong() })
                browser.setMediaItems(children)
                browser.prepare()
//                browser.playWhenReady = true
            },
            executor
        )
    }

    private fun setController() {
        val controller = this.browser ?: return

        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    _playerState.value = playerState.value.copy(item = mediaItem?.toSong())
                    trackPlayerProgress()
                }

                override fun onTracksChanged(tracks: Tracks) {
//                    playerView.setShowSubtitleButton(tracks.isTypeSupported(C.TRACK_TYPE_TEXT))
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    _playerState.value = playerState.value.copy(isPlaying = isPlaying)
                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    _playerState.value = playerState.value.copy(error = error)

                }
            }
        )
    }

    private fun trackPlayerProgress() {
        progressTrackingJob?.cancel()
        progressTrackingJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {

                val progress = withContext(Dispatchers.Main) {
                    browser?.currentPosition ?: 0L
                }
                val duration = withContext(Dispatchers.Main) {
                    browser?.duration ?: 0L
                }

                _playerState.value =
                    playerState.value.copy(
                        duration = duration,
                        progress = progress
                    )
                delay(Constants.UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }


    private fun releaseBrowser() {
        MediaBrowser.releaseFuture(browserFuture)
    }


    override fun onCleared() {
        releaseBrowser()
        progressTrackingJob?.cancel()
        super.onCleared()
    }

    fun onPlayPauseClick() {
        val controller = browser ?: return
        controller.playWhenReady = !controller.playWhenReady
    }

    fun onItemClick(position: Int) {
        val songs = songsState.value.songs
        val song = songs[position]
        //updating the metaData Here...
        song.lastPlayedDate = System.currentTimeMillis()
        song.playedCount++

        val mediaItemIndex = song.mediaItem.fixedItemIndex

        browser?.let { controller ->
            if (controller.currentMediaItemIndex == mediaItemIndex) {
                controller.playWhenReady = !controller.playWhenReady
            } else {
                controller.seekToDefaultPosition(mediaItemIndex)
                controller.playWhenReady = true
            }
        }
    }

    fun onPlayNextClick() {
        browser?.seekToNext()
    }

    fun onPreviousButtonClick() {
        browser?.seekToPrevious()
    }

    fun onSortActionChange(sortBy: SortBy) {
        _sortBy.value = sortBy
    }

    fun onSeekToClick(positionInMs: Long) {
        _playerState.value = playerState.value.copy(progress = positionInMs)
        browser?.seekTo(positionInMs)
    }

    override fun onChildrenChanged(
        browser: MediaBrowser,
        parentId: String,
        itemCount: Int,
        params: MediaLibraryService.LibraryParams?
    ) {
        getChildren(parentId)
    }


    data class PlayerState(
        val isPlaying: Boolean = false,
        val item: Song? = null,
        val error: PlaybackException? = null,
        val progress: Long = 0L,
        val duration: Long = 0L,
    ) {
        companion object {
            val NotPlaying = PlayerState()
        }
    }

    data class SongsState(
        val isLoading: Boolean = false,
        val songs: List<Song> = emptyList()
    )

}
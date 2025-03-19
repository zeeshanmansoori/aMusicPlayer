package com.zee.amusicplayer.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaBrowser
import androidx.media3.session.MediaController
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionCommands
import androidx.media3.session.SessionError
import androidx.media3.session.SessionResult
import androidx.media3.session.SessionToken
import androidx.navigation.NavHostController
import com.google.common.util.concurrent.ListenableFuture
import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.domain.model.toSong
import com.zee.amusicplayer.domain.useCase.album.AlbumUseCase
import com.zee.amusicplayer.domain.useCase.artist.ArtistsUseCase
import com.zee.amusicplayer.domain.useCase.playlist.PlayListUseCase
import com.zee.amusicplayer.service.MusicService
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.SortBy
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


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible = _isSearchVisible.asStateFlow()

    private val _filterKey = MutableStateFlow("")

    private val executor = ContextCompat.getMainExecutor(application)
    private lateinit var navController: NavHostController

    private val browserListener = object : MediaBrowser.Listener {
        override fun onAvailableSessionCommandsChanged(
            controller: MediaController,
            commands: SessionCommands
        ) {
            Log.d("zeeshan", "onAvailableSessionCommandsChanged: ")
            super.onAvailableSessionCommandsChanged(controller, commands)
        }

        @SuppressLint("UnsafeOptInUsageError")
        override fun onCustomLayoutChanged(
            controller: MediaController,
            layout: MutableList<CommandButton>
        ) {
            Log.d("zeeshan", "onCustomLayoutChanged: ")
            super.onCustomLayoutChanged(controller, layout)
        }

        override fun onExtrasChanged(controller: MediaController, extras: Bundle) {
            super.onExtrasChanged(controller, extras)
            Log.d("zeeshan", "onExtrasChanged: ")
        }

        @SuppressLint("UnsafeOptInUsageError")
        override fun onMediaButtonPreferencesChanged(
            controller: MediaController,
            mediaButtonPreferences: MutableList<CommandButton>
        ) {
            Log.d("zeeshan", "onMediaButtonPreferencesChanged: ")
            super.onMediaButtonPreferencesChanged(controller, mediaButtonPreferences)
        }

        @SuppressLint("UnsafeOptInUsageError")
        override fun onSessionActivityChanged(
            controller: MediaController,
            sessionActivity: PendingIntent
        ) {
            Log.d("zeeshan", "onSessionActivityChanged: ")
            super.onSessionActivityChanged(controller, sessionActivity)
        }

        override fun onSetCustomLayout(
            controller: MediaController,
            layout: MutableList<CommandButton>
        ): ListenableFuture<SessionResult> {
            Log.d("zeeshan", "onSetCustomLayout: ")
            return super.onSetCustomLayout(controller, layout)
        }

        override fun onCustomCommand(
            controller: MediaController,
            command: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            Log.d("zeeshan", "onCustomCommand: ")
            return super.onCustomCommand(controller, command, args)
        }

        override fun onSearchResultChanged(
            browser: MediaBrowser,
            query: String,
            itemCount: Int,
            params: MediaLibraryService.LibraryParams?
        ) {
            Log.d("zeeshan", "onSearchResultChanged: ")
            super.onSearchResultChanged(browser, query, itemCount, params)
        }

        override fun onChildrenChanged(
            browser: MediaBrowser,
            parentId: String,
            itemCount: Int,
            params: MediaLibraryService.LibraryParams?
        ) {
            Log.d("zeeshan", "onChildrenChanged: ")
            getChildren(parentId)
        }

        override fun onDisconnected(controller: MediaController) {
            Log.d("zeeshan", "onDisconnected: ")
            super.onDisconnected(controller)
        }

        @OptIn(UnstableApi::class)
        override fun onError(
            controller: MediaController,
            @SuppressLint("UnsafeOptInUsageError") sessionError: SessionError
        ) {
            Log.d("zeeshan", "onError: error ${sessionError.message}")
            super.onError(controller, sessionError)
        }

    }

    @SuppressLint("UnsafeOptInUsageError")
    private val browserFuture = MediaBrowser.Builder(
        application,
        SessionToken(application, ComponentName(application, MusicService::class.java))
    ).setListener(browserListener).buildAsync()

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


    val albumUseCase by lazy { AlbumUseCase(songsState,navController, viewModelScope) }
    val artistsUseCase by lazy { ArtistsUseCase(songsState, viewModelScope) }
    val playListUseCase by lazy { PlayListUseCase(viewModelScope) }

    private var job: Job? = null

    init {
        browserFuture.addListener({
            val browser = this.browser ?: return@addListener
            val rootFuture = browser.getLibraryRoot(null)
            rootFuture.addListener({
                val root = rootFuture.get().value!!
                browser.subscribe(root.mediaId, null)
                setController()
            }, executor)

        }, executor)

    }

    fun triggerFetchMusicWorker() {
        _songsState.value = _songsState.value.copy(isLoading = true)
        MusicService.scheduleFetchTask(getApplication())
    }

    fun onFilterKeyChanged(key: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(Constants.DEBOUNCE_TIME)
            _filterKey.value = key
        }
        job?.start()
    }

    fun changeSearchVisibility(isVisible: Boolean) {
        _isSearchVisible.value = isVisible
        if (!isVisible) _filterKey.value = ""
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
                    Log.d("zeeshan", "onPlayerError: $error")
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
        Log.d(
            "zeeshan",
            "onPlayPauseClick: controller $browser currentMediaItem ${browser?.currentMediaItem} index ${browser?.currentMediaItemIndex}"
        )
        val controller = browser ?: return
        val wantToPlay = !controller.playWhenReady
        if (wantToPlay && controller.playbackState == Player.STATE_IDLE) {
            controller.prepare()
        }
        controller.playWhenReady = wantToPlay
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

        if (isSearchVisible.value) {
            _filterKey.value = ""
            _isSearchVisible.value = false
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

    fun setNavController(controller: NavHostController) {
        this.navController = controller
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
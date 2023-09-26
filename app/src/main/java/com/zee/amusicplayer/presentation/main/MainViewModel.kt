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
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.zee.amusicplayer.data.models.SortByE
import com.zee.amusicplayer.service.MusicService
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.dateModified
import com.zee.amusicplayer.utils.itemIndex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("UnsafeOptInUsageError")
    private val browserFuture = MediaBrowser.Builder(
        application,
        SessionToken(application, ComponentName(application, MusicService::class.java))
    )
        .buildAsync()

    private val browser: MediaBrowser?
        get() = if (browserFuture.isDone && !browserFuture.isCancelled) browserFuture.get() else null

    private val _playerScreenState = MutableStateFlow<PlayerScreenState>(PlayerScreenState.Loading)
    val playerScreenState: StateFlow<PlayerScreenState> = _playerScreenState

    private val _playerState = MutableStateFlow(PlayerState.EMPTY)
    val playerState = _playerState.asStateFlow()

    private val executor = ContextCompat.getMainExecutor(application)
    private var progressTrackingJob: Job? = null

    private val _sortByE = MutableStateFlow<SortByE>(SortByE.Name)
    val sortByE = _sortByE.asStateFlow()

    init {
        browserFuture.addListener({
            pushRoot()
            setController()
        }, executor)

        viewModelScope.launch(Dispatchers.IO) {
            _sortByE.collectLatest { sortBy ->
                val state = playerScreenState.value

                if (state is PlayerScreenState.Loaded) {
                    val oldList = state.items.toMutableList()
                    val list = when(sortBy){
                        SortByE.Name -> oldList.sortedBy { it.mediaMetadata.title.toString() }
                        SortByE.LastAdded -> oldList.sortedBy { it.dateModified }
                        else -> oldList

                    }
                    _playerScreenState.value = state.copy(items = list)
                }
            }
        }
    }

    private fun pushRoot() {

        val browser = this.browser ?: return
        val rootFuture = browser.getLibraryRoot(/* params= */ null)
        rootFuture.addListener(
            {
                val result: LibraryResult<MediaItem> = rootFuture.get()!!
                val root: MediaItem = result.value!!
                displayChildrenList(root)

            },
            executor
        )
    }

    private fun displayChildrenList(mediaItem: MediaItem) {
        val browser = this.browser ?: return

        val childrenFuture =
            browser.getChildren(
                mediaItem.mediaId,
                /* page= */ 0,
                /* pageSize= */ Int.MAX_VALUE,
                /* params= */ null
            )

        childrenFuture.addListener(
            {
                val result = childrenFuture.get()!!
                val children = result.value!!
                // setting itemIndex to track the position of mediaItem within player
                children.forEachIndexed { index, item ->
                    item.itemIndex = index
                }
                _playerScreenState.value = PlayerScreenState.Loaded(children.toList())
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
                    _playerState.value = playerState.value.copy(item = mediaItem)
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
        val index = browser?.currentMediaItemIndex ?: return
        onItemClick(index)
    }

    fun onItemClick(position: Int) {
        browser?.let { controller ->
            if (controller.currentMediaItemIndex == position) {
                controller.playWhenReady = !controller.playWhenReady
            } else {
                controller.seekToDefaultPosition(position)
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

    fun onSortActionChange(sortByE: SortByE) {
        _sortByE.value = sortByE
    }

    fun onSeekToClick(positionInMs: Long) {
        _playerState.value = playerState.value.copy(progress = positionInMs)
        browser?.seekTo(positionInMs)
    }


    data class PlayerState(
        val isPlaying: Boolean = false,
        val item: MediaItem? = null,
        val error: PlaybackException? = null,
        val progress: Long = 0L,
        val duration: Long = 0L,
    ) {
        companion object {
            val EMPTY = PlayerState()
        }
    }

    sealed class PlayerScreenState(open val items: List<MediaItem>) {
        object Loading : PlayerScreenState(emptyList())
        data class Loaded(override val items: List<MediaItem>) : PlayerScreenState(items)
    }

}
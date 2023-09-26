package com.zee.amusicplayer.presentation.home

import android.app.Application
import android.content.ComponentName
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.zee.amusicplayer.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val browserFuture = MediaBrowser.Builder(
        application,
        SessionToken(application, ComponentName(application, MusicService::class.java))
    )
        .buildAsync()

    private val browser: MediaBrowser?
        get() = if (browserFuture.isDone && !browserFuture.isCancelled) browserFuture.get() else null

    private val _items = MutableStateFlow<List<MediaItem>>(emptyList())
    val items: StateFlow<List<MediaItem>> = _items

    private val _item = MutableStateFlow<MediaItem>(MediaItem.EMPTY)
    val item = _item.asStateFlow()

    val currentIndex = _item.map {
        browser?.currentMediaItemIndex ?: 0
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)


    private val executor = ContextCompat.getMainExecutor(application)


    init {
        browserFuture.addListener({
            pushRoot()
            setController()
        }, executor)
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
                _items.value = children.toList()
                browser.setMediaItems(children)
                browser.prepare()
                browser.playWhenReady = true
            },
            executor
        )
    }

    private fun setController() {
        val controller = this.browser ?: return

//        playerView.player = controller

//        updateMediaMetadataUI(controller.mediaMetadata)
//        playerView.setShowSubtitleButton(controller.currentTracks.isTypeSupported(C.TRACK_TYPE_TEXT))

        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    _item.value = (mediaItem ?: MediaItem.EMPTY)
                }

                override fun onTracksChanged(tracks: Tracks) {
//                    playerView.setShowSubtitleButton(tracks.isTypeSupported(C.TRACK_TYPE_TEXT))
                }
            }
        )
    }


    private fun releaseBrowser() {
        MediaBrowser.releaseFuture(browserFuture)
    }


    override fun onCleared() {
        releaseBrowser()
        super.onCleared()
    }

    fun onItemClick(position: Int) {
        browser?.let { controller ->
            if (controller.currentMediaItemIndex == position) {
                controller.playWhenReady = !controller.playWhenReady
            } else {
                controller.seekToDefaultPosition(/* mediaItemIndex= */ position)
            }
        }
    }
}
package com.zee.amusicplayer.service

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.zee.amusicplayer.utils.MediaItemHelper

@SuppressLint("UnsafeOptInUsageError")
class MediaLibrarySessionCallback(
    private val player: ExoPlayer,
) :
    MediaLibraryService.MediaLibrarySession.Callback {

//    val customCommands: List<CommandButton> = listOf(
//        getShuffleCommandButton(
//            SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON, Bundle.EMPTY)
//        ),
//        getShuffleCommandButton(
//            SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF, Bundle.EMPTY)
//        )
//    )


    override fun onGetLibraryRoot(
        session: MediaLibraryService.MediaLibrarySession,
        browser: ControllerInfo,
        params: MediaLibraryService.LibraryParams?
    ): ListenableFuture<LibraryResult<MediaItem>> {

        return Futures.immediateFuture(LibraryResult.ofItem(MediaItemHelper.Root, params))
    }

    override fun onGetChildren(
        session: MediaLibraryService.MediaLibrarySession,
        browser: ControllerInfo,
        parentId: String,
        page: Int,
        pageSize: Int,
        params: MediaLibraryService.LibraryParams?
    ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
        val children = MediaItemHelper.getChildren(parentId)
        return Futures.immediateFuture(LibraryResult.ofItemList(children, params))
    }

    override fun onGetItem(
        session: MediaLibraryService.MediaLibrarySession,
        browser: ControllerInfo,
        mediaId: String
    ): ListenableFuture<LibraryResult<MediaItem>> {
        val item = MediaItemHelper.getChild(mediaId)
        return Futures.immediateFuture(LibraryResult.ofItem(item, null))
    }

//    override fun onGetItem(
//        session: MediaLibraryService.MediaLibrarySession,
//        browser: ControllerInfo,
//        mediaId: String
//    ): ListenableFuture<LibraryResult<MediaItem>> {
//        val item =
//            MediaItemTree.getItem(mediaId)
//                ?: return Futures.immediateFuture(
//                    LibraryResult.ofError(SessionError.ERROR_BAD_VALUE)
//                )
//        return Futures.immediateFuture(LibraryResult.ofItem(item, /* params= */ null))
//    }

//    override fun onSubscribe(
//        session: MediaLibraryService.MediaLibrarySession,
//        browser: ControllerInfo,
//        parentId: String,
//        params: MediaLibraryService.LibraryParams?
//    ): ListenableFuture<LibraryResult<Void>> {
//        val children =
//            MediaItemTree.getChildren(parentId)
//                ?: return Futures.immediateFuture(
//                    LibraryResult.ofError(SessionError.ERROR_BAD_VALUE)
//                )
//        session.notifyChildrenChanged(browser, parentId, children.size, params)
//        return Futures.immediateFuture(LibraryResult.ofVoid())
//    }


    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: ControllerInfo,
        mediaItems: List<MediaItem>
    ): ListenableFuture<List<MediaItem>> {
        val newList = mediaItems.map {
            it.buildUpon().setUri(it.requestMetadata.mediaUri).build()
        }
        return Futures.immediateFuture(newList)
    }

//    private fun getMediaItemFromSearchQuery(query: String): MediaItem {
//        // Only accept query with pattern "play [Title]" or "[Title]"
//        // Where [Title]: must be exactly matched
//        // If no media with exact name found, play a random media instead
//        val mediaTitle =
//            if (query.startsWith("play ", ignoreCase = true)) {
//                query.drop(5)
//            } else {
//                query
//            }
//
//        return MediaItemTree.getItemFromTitle(mediaTitle) ?: MediaItemTree.getRandomItem()
//    }


//    @SuppressLint("UnsafeOptInUsageError")
//    private fun getShuffleCommandButton(sessionCommand: SessionCommand): CommandButton {
//        val isOn = sessionCommand.customAction == CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON
//        return CommandButton.Builder()
//            .setDisplayName(
//                if (isOn) "shuffle on"
//                else "shuffle off"
//
//            )
//            .setSessionCommand(sessionCommand)
//            .setIconResId(if (isOn) androidx.media3.ui.R.drawable.exo_icon_shuffle_off else androidx.media3.ui.R.drawable.exo_icon_shuffle_on)
//            .build()
//    }

//    @OptIn(UnstableApi::class)
//    override fun onConnect(
//        session: MediaSession,
//        controller: ControllerInfo
//    ): MediaSession.ConnectionResult {
//        val availableSessionCommands =
//            MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS.buildUpon()
//        for (commandButton in customCommands) {
//            // Add custom command to available session commands.
//            commandButton.sessionCommand?.let { availableSessionCommands.add(it) }
//        }
//        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
//            .setAvailableSessionCommands(availableSessionCommands.build())
//            .build()
//    }

//    override fun onCustomCommand(
//        session: MediaSession,
//        controller: ControllerInfo,
//        customCommand: SessionCommand,
//        args: Bundle
//    ): ListenableFuture<SessionResult> {
//        if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON == customCommand.customAction) {
//            // Enable shuffling.
//            player.shuffleModeEnabled = true
//            // Change the custom layout to contain the `Disable shuffling` command.
//            session.setCustomLayout(ImmutableList.of(customCommands[1]))
//        } else if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF == customCommand.customAction) {
//            // Disable shuffling.
//            player.shuffleModeEnabled = false
//            // Change the custom layout to contain the `Enable shuffling` command.
//            session.setCustomLayout(ImmutableList.of(customCommands[0]))
//        }
//        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
//    }


}

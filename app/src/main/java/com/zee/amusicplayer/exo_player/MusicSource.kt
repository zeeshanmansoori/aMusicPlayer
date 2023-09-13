package com.zee.amusicplayer.exo_player

interface MusicSource  {
    suspend fun load()
    fun whenReady(performAction: (Boolean) -> Unit): Boolean
}

enum class MusicSourceState {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}

abstract class AbstractMusicSource : MusicSource {

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    var state: MusicSourceState = MusicSourceState.STATE_CREATED
        set(value) {
            if (value == MusicSourceState.STATE_INITIALIZED || value == MusicSourceState.STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == MusicSourceState.STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    override fun whenReady(performAction: (Boolean) -> Unit): Boolean {
        return when (state) {
            MusicSourceState.STATE_CREATED, MusicSourceState.STATE_INITIALIZING -> {
                onReadyListeners += performAction
                false
            }
            else -> {
                performAction(state != MusicSourceState.STATE_ERROR)
                true
            }
        }

    }
}
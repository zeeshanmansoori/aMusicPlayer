package com.zee.amusicplayer.utils

import android.content.ContentResolver
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore

class MediaStoreObserver(
    private val contentResolver: ContentResolver,
    private val onMediaChanged: () -> Unit
) : ContentObserver(Handler(Looper.getMainLooper())) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        onMediaChanged()  // Trigger UI update
    }

    fun register() {
        contentResolver.registerContentObserver(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            true, this
        )
    }

    fun unregister() {
        contentResolver.unregisterContentObserver(this)
    }
}

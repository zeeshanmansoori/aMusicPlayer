package com.zee.amusicplayer.utils

import android.annotation.SuppressLint
import android.app.RecoverableSecurityException
import android.content.Context
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.zee.amusicplayer.domain.model.Song

sealed class DropDownOption(
    val name: String,
    val isEnabled: Boolean
) {


    object Delete : DropDownOption("Delete", isEnabled = true) {


        @SuppressLint("NewApi")
        fun deleteSong(
            song: Song,
            context: Context,
            askPermission: ((IntentSenderRequest) -> Unit)? = null,
        ): Boolean {
            try {
                val uri = song.mediaItem.requestMetadata.mediaUri ?: return false

                val contentResolver = context.contentResolver
                val row = contentResolver.delete(uri, null, null)
                return row != 0

            } catch (e: RecoverableSecurityException) {

                val intentSender = e.userAction.actionIntent.intentSender
                val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()

                askPermission?.invoke(intentSenderRequest)
                return false
            } catch (e: Exception) {
                Log.e("zeeshan", "Error deleting file: ${e.message}")
                return false
            }
        }
    }


    object PlayNext : DropDownOption("Play Next", isEnabled = false) {

    }


}
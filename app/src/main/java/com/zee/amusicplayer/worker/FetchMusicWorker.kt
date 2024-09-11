package com.zee.amusicplayer.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.zee.amusicplayer.di.AppModule
import com.zee.amusicplayer.domain.utils.Constants
import com.zee.amusicplayer.domain.utils.MediaItemHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchMusicWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val notificationId = 1

        WorkerUtils.makeStatusNotification(
            notificationId,
            "Fetching...",
            "Scanning Music files",
            applicationContext
        )

        val repository = AppModule.provideSongRepository()
        val inputName = inputData.getString("parentId") ?: ""
        val songs = repository.getSongs()
        MediaItemHelper.addChildren(inputName, songs)
        val output = workDataOf(
            Constants.PARENT_ID_KEY to inputName,
            Constants.ITEM_COUNT_KEY to songs.size
        )
        WorkerUtils.dismissNotification(notificationId, applicationContext)
        Result.success(output)
    }

}
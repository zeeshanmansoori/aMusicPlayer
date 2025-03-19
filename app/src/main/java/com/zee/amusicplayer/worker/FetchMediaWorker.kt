package com.zee.amusicplayer.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.zee.amusicplayer.di.AppModule
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchMediaWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val repository = AppModule.provideSongRepository()
        val inputName = inputData.getString("parentId") ?: ""

        repository.updateMediaTree()
        val songs = repository.getMediaItems()

        val output = workDataOf(
            Constants.PARENT_ID_KEY to inputName,
            Constants.ITEM_COUNT_KEY to songs.size
        )

        Result.success(output)
    }

}
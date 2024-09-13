package com.zee.amusicplayer.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.zee.amusicplayer.di.AppModule
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveMetaDataWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val repository = AppModule.provideSongRepository()

        repository.updateMediaTree()
        val rowsAffected = repository.updateDataBaseWithMetaData()
        if (rowsAffected != 0) {
            Result.success()

        }
        Result.failure()
    }

}
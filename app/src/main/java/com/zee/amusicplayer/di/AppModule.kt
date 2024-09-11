package com.zee.amusicplayer.di

import android.annotation.SuppressLint
import android.app.Application
import com.zee.amusicplayer.data.dataSource.AudioOfflineDataSource
import com.zee.amusicplayer.data.repository.SongRepositoryImpl
import com.zee.amusicplayer.domain.repository.ISongRepository

object AppModule {

    @SuppressLint("StaticFieldLeak")
    private lateinit var application: Application
    private fun providesDataSource(): AudioOfflineDataSource {
        return AudioOfflineDataSource(application)
    }

    fun init(application: Application) {
        this.application = application
    }

    fun provideSongRepository(): ISongRepository {
        return SongRepositoryImpl(providesDataSource())
    }
}
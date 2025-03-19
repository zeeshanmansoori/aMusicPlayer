package com.zee.amusicplayer.di

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.zee.amusicplayer.data.dataSource.AppDatabase
import com.zee.amusicplayer.data.dataSource.AudioOfflineDataSource
import com.zee.amusicplayer.data.repository.SongRepositoryImpl
import com.zee.amusicplayer.domain.repository.ISongRepository
import com.zee.amusicplayer.domain.useCase.sorting.SortByNameUseCase

object AppModule {

    @SuppressLint("StaticFieldLeak")
    private lateinit var application: Application
     fun providesDataSource(): AudioOfflineDataSource {
        return AudioOfflineDataSource(application)
    }

    fun init(application: Application) {
        this.application = application
    }

    fun provideSongRepository(): ISongRepository {
        return SongRepositoryImpl(providesDataSource(), provideDatabase())
    }


    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "a-music-app-db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    ///UseCases
    fun <T> provideSortByNameUseCase(): SortByNameUseCase<T> {
        return SortByNameUseCase()
    }
}

package com.zee.amusicplayer.di

import android.app.Application
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.domain.use_case.GetAllSongsUseCase
import com.zee.amusicplayer.feature_songs.data_source.AudioOfflineDataSource
import com.zee.amusicplayer.feature_songs.repository.SongRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDataSource(app: Application): AudioOfflineDataSource {
        return AudioOfflineDataSource(app)
    }


    @Provides
    @Singleton
    fun provideSongRepository(dataSource: AudioOfflineDataSource): SongRepository {
        return SongRepositoryImpl(dataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideSongUseCase(repository: SongRepository): GetAllSongsUseCase {
        return GetAllSongsUseCase(repository)
    }


}
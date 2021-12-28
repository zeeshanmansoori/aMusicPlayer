package com.zee.amusicplayer.di

import android.app.Application
import com.zee.amusicplayer.domain.repository.AlbumRepository
import com.zee.amusicplayer.domain.repository.ArtistRepository
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.domain.use_cases.GetAllAlbumUseCase
import com.zee.amusicplayer.domain.use_cases.GetAllArtistUseCase
import com.zee.amusicplayer.domain.use_cases.GetAllSongsUseCase
import com.zee.amusicplayer.feature_albums.repository.AlbumRepositoryImpl
import com.zee.amusicplayer.feature_artists.repository.ArtistRepositoryImpl
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
    fun provideAlbumRepository(repository: SongRepository): AlbumRepository {
        return AlbumRepositoryImpl(songRepository = repository)
    }


    @Provides
    @Singleton
    fun provideArtistRepository(repository: SongRepository): ArtistRepository {
        return ArtistRepositoryImpl(songRepository = repository)
    }

    @Provides
    @Singleton
    fun provideSongUseCase(repository: SongRepository): GetAllSongsUseCase {
        return GetAllSongsUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideAlbumUseCase(repository: AlbumRepository): GetAllAlbumUseCase {
        return GetAllAlbumUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideArtistUseCase(repository: ArtistRepository): GetAllArtistUseCase {
        return GetAllArtistUseCase(repository)
    }


}
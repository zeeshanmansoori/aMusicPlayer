package com.zee.amusicplayer.di

import android.app.Application
import android.content.ComponentName
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.SessionToken
import com.zee.amusicplayer.domain.repository.AlbumRepository
import com.zee.amusicplayer.domain.repository.ArtistRepository
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.domain.use_cases.GetAllAlbumUseCase
import com.zee.amusicplayer.domain.use_cases.GetAllArtistUseCase
import com.zee.amusicplayer.domain.use_cases.GetAllSongsUseCase
import com.zee.amusicplayer.exo_player.PlaybackService
import com.zee.amusicplayer.feature_albums.repository.AlbumRepositoryImpl
import com.zee.amusicplayer.feature_artists.repository.ArtistRepositoryImpl
import com.zee.amusicplayer.feature_songs.data_source.AudioOfflineDataSource
import com.zee.amusicplayer.feature_songs.repository.SongRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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


    @Provides
    @Singleton
    fun provideAudioAttributes() =
        AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context, audioAttributes: AudioAttributes) =
        ExoPlayer.Builder(context).build()
            .apply {
                setAudioAttributes(audioAttributes, true)
                setHandleAudioBecomingNoisy(true)
            }


    @Provides
    @Singleton
    fun provideSessionToken(@ApplicationContext context: Context): SessionToken {
        return SessionToken(context, ComponentName(context, PlaybackService::class.java))
    }

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

}
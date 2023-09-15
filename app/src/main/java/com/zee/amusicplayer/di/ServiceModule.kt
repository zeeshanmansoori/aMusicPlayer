package com.zee.amusicplayer.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.zee.amusicplayer.presentation.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {


    @ServiceScoped
    @Provides
    fun provideDataSourceFactory(
        @ApplicationContext context: Context
    ): DefaultDataSource.Factory {

        return DefaultDataSource.Factory(context)
    }

    @ServiceScoped
    @Provides
    fun provideMediaSession(@ApplicationContext context: Context, player: ExoPlayer): MediaSession {

        val activityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_MUTABLE)

        return MediaSession.Builder(context, player)
            .setSessionActivity(pendingIntent)
            .build()

    }

}
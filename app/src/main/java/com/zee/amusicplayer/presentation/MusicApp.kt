package com.zee.amusicplayer.presentation

import android.app.Application
import com.zee.amusicplayer.di.AppModule

class MusicApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppModule.init(this)
    }
}
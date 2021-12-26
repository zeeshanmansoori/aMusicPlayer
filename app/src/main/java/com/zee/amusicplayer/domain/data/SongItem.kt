package com.zee.amusicplayer.domain.data

import androidx.annotation.DrawableRes

data class SongItem(val title: String, @DrawableRes val icon: Int?, val albumName: String? = null)


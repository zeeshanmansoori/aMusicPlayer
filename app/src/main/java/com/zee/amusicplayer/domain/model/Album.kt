package com.zee.amusicplayer.domain.model

data class Album(
    val id: String,
    val title: String,
    val songs: List<Song>
)

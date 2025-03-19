package com.zee.amusicplayer.domain.model

data class Artist(
    val id: String,
    val name: String,
    val songs: List<Song>
)

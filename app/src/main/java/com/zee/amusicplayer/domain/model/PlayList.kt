package com.zee.amusicplayer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayList(
    val name: String,
    val songs: List<String>,
    @PrimaryKey(autoGenerate = true)
    val id: String = ""
)
package com.zee.amusicplayer.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "MediaMedaData")
@Parcelize
data class OtherMediaMetaData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val isFavourite: Boolean = false,
    val playedCount: Int = 0,
    val lastPlayedDate: Long = 0L,
):Parcelable
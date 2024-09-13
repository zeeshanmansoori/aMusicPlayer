package com.zee.amusicplayer.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zee.amusicplayer.data.db.dao.MediaMetaDataDao
import com.zee.amusicplayer.data.db.entity.OtherMediaMetaData

@Database(entities = [OtherMediaMetaData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaMetaDataDao(): MediaMetaDataDao
}
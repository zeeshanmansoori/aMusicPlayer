package com.zee.amusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zee.amusicplayer.data.db.entity.OtherMediaMetaData

@Dao
interface MediaMetaDataDao {
    @Query("SELECT * FROM mediamedadata")
    suspend fun getAll(): List<OtherMediaMetaData>

    @Insert
    fun insertAll(vararg songEntities: OtherMediaMetaData)

    @Delete
    fun delete(mediaMetaData: OtherMediaMetaData)

    @Update
    suspend fun updateAll(entities: List<OtherMediaMetaData>):Int
}
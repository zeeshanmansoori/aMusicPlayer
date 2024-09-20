package com.zee.amusicplayer.data.repository

import androidx.media3.common.MediaItem
import com.zee.amusicplayer.data.dataSource.AppDatabase
import com.zee.amusicplayer.data.dataSource.AudioOfflineDataSource
import com.zee.amusicplayer.data.db.entity.OtherMediaMetaData
import com.zee.amusicplayer.di.AppModule
import com.zee.amusicplayer.domain.repository.ISongRepository
import com.zee.amusicplayer.utils.MediaItemHelper
import com.zee.amusicplayer.utils.fixedItemIndex
import com.zee.amusicplayer.utils.otherMediaMetaData

class SongRepositoryImpl(
    private val dataSource: AudioOfflineDataSource,
    private val database: AppDatabase,
) : ISongRepository {

    override fun getRootItem(): MediaItem {
        return MediaItemHelper.Root
    }

    override fun getMediaItem(id: String): MediaItem {
        return MediaItemHelper.getChild(id)
    }

    override fun getMediaItems(): List<MediaItem> {
        return MediaItemHelper.getChildren(getRootItem().mediaId)
    }

    override suspend fun updateMediaTree() {
        val data = dataSource.songs()
        val dao = database.mediaMetaDataDao()
        val metaData = mutableMapOf<String, OtherMediaMetaData>()
        dao.getAll().map {
            metaData[it.id] = it
        }
        val songs = AppModule.provideSortByNameUseCase<MediaItem>()(data) { item ->
            item.mediaMetadata.title.toString()
        }

        songs.forEachIndexed { index, mediaItem ->
            mediaItem.otherMediaMetaData = metaData[mediaItem.mediaId]
            mediaItem.fixedItemIndex = index
        }


        MediaItemHelper.addChildren(getRootItem().mediaId, songs)
    }

    override suspend fun updateDataBaseWithMetaData(): Int {
        val dao = database.mediaMetaDataDao()
        val metaDataList = getMediaItems().mapNotNull {
            it.otherMediaMetaData
        }
        return dao.updateAll(metaDataList)
    }

}
package com.zee.amusicplayer.domain.model

import androidx.media3.common.MediaItem
import com.zee.amusicplayer.data.db.entity.OtherMediaMetaData
import com.zee.amusicplayer.domain.utils.dateModified
import com.zee.amusicplayer.domain.utils.otherMediaMetaData

data class Song(
    val id: String,
    val artUri: String,
    val title: String,
    val artistName: String,
    val artistId: String,
    val albumId: String,
    val albumName: String,
    val mediaItem: MediaItem,
    val lastModified: Long = 0L
) {
    var lastPlayedDate: Long
        set(value) {
            mediaItem.otherMediaMetaData = (mediaItem.otherMediaMetaData ?: OtherMediaMetaData(id)).copy(lastPlayedDate = value)
        }
        get() = mediaItem.otherMediaMetaData?.lastPlayedDate ?: 0L

    var playedCount: Int
        set(value) {
            mediaItem.otherMediaMetaData = (mediaItem.otherMediaMetaData ?: OtherMediaMetaData(id)).copy(playedCount = value)
        }
        get() = mediaItem.otherMediaMetaData?.playedCount ?: 0

}
fun MediaItem.toSong(): Song {
    return Song(
        id = mediaId,
        artUri = requestMetadata.mediaUri.toString(),
        title = mediaMetadata.title.toString(),
        artistName = mediaMetadata.artist.toString(),
        artistId = mediaMetadata.artist.toString(),
        albumId = mediaMetadata.albumTitle.toString(),
        albumName = mediaMetadata.albumTitle.toString(),
        lastModified = dateModified,
        mediaItem = this,
    )
}
fun Song.toArtist(): Artist {
    return Artist(artistId, artistName, listOf(this))
}
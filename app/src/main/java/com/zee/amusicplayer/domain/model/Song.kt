package com.zee.amusicplayer.domain.model

import androidx.media3.common.MediaItem

data class Song(
    val id: String,
    val artUri: String,
    val title: String,
    val artistName: String,
    val artistId: String,
    val albumId: String,
    val albumName: String,
    val mediaItem: MediaItem,
    val mostPlayedCount:Int = 0,
    val lastPlayedDate:Long = 0L,

)
fun MediaItem.toSong(): Song {
    return Song(
        id = mediaId,
        artUri = requestMetadata.mediaUri.toString(),
        title = mediaMetadata.title.toString(),
        artistName = mediaMetadata.artist.toString(),
        artistId = mediaMetadata.artist.toString(),
        albumId = mediaMetadata.albumTitle.toString(),
        albumName = mediaMetadata.albumTitle.toString(),
        mediaItem = this
    )
}
fun Song.toArtist(): Artist {
    return Artist(artistId, artistName, listOf(this))
}
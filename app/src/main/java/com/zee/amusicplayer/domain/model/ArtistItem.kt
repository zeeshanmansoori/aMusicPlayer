package com.zee.amusicplayer.domain.model

data class ArtistItem(
    val id: Long,
    val artistName: String,
    val albums: List<AlbumItem>,
) {


    companion object {
        fun tempList(): List<ArtistItem> {
            return List(30) { index ->
                ArtistItem(index.toLong(), "artist $index", emptyList())
            }
        }
    }

    val allSongs: List<SongItem>
        get() {
            return albums.flatMap { it.songs }
        }

    val songsCount: Int
        get() {
            return allSongs.size
        }
    val albumCount: Int
        get() {
            return albums.size
        }

    val totalDuration: Long
        get() {
            return allSongs.sumOf { it.duration }
        }

}



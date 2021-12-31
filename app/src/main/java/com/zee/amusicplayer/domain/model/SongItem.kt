package com.zee.amusicplayer.domain.model

data class SongItem(
    val id: Long = 56,
    val title: String = "",
    val trackNumber: Int = 0,
    val year: Int = 0,
    val duration: Long = 0L,
    val data: String? = null,
    val dateModified: Long = 0L,
    val albumId: Long = 0L,
    //folder name
    val albumName: String = "",
    val artistId: Long = 0L,
    val artistName: String = "",
    val composer: String? = null,
    val contentUri: String? = null,
    val albumUri:String? = null
) {
    companion object {

        fun List<SongItem>.toAlbums(): List<AlbumItem> {
            return groupBy { it.albumId }.map { AlbumItem(it.key, it.value) }.toList()
        }

        fun tempList(): MutableList<SongItem> {
            return MutableList(100) { index ->


                SongItem(
                    id = index.toLong(),
                    title = "imran is my borther and i linke nothing to listen",
                    trackNumber = index,
                    year = 2021,
                    duration = 5000,
                    dateModified = 6465,
                    albumId = index.toLong() % 10,
                    albumName = "AlBMN",
                    artistId = 65,
                    artistName = "artisits zee",
                    composer = "zee is composer",
                )

            }


        }

        fun getEmptySong():SongItem = SongItem(
            id = -1,
            title = "",
            trackNumber = -1,
            year = -1,
            duration = -1,
            data = "",
            dateModified = -1,
            albumId = -1,
            albumName = "",
            artistId = -1,
            artistName = "",
            composer = "",
        )

    }
}


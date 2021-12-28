package com.zee.amusicplayer.domain.model

data class SongItem(
    val id: Long,
    val title: String,
    val trackNumber: Int,
    val year: Int,
    val duration: Long,
    val data: String? = null,
    val dateModified: Long,
    val albumId: Long,
    //folder name
    val albumName: String,
    val artistId: Long,
    val artistName: String,
    val composer: String? = null,
    val albumArtist: String? = null
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
            albumArtist = ""
        )

    }
}


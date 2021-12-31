package com.zee.amusicplayer.domain.model

data class AlbumItem(
    val id: Long,
    val songs: List<SongItem>
) {

    val albumName: String
        get() = safeGetFirstSong().albumName

    val artistId: Long
        get() = safeGetFirstSong().artistId

    val artistName: String
        get() = safeGetFirstSong().artistName

    val year: Int
        get() = safeGetFirstSong().year

    val dateModified: Long
        get() = safeGetFirstSong().dateModified

    val songCount: Int
        get() = songs.size

    private fun safeGetFirstSong(): SongItem {
        return songs.firstOrNull() ?: SongItem.getEmptySong()
    }

    companion object {

        fun List<AlbumItem>.toArtists(): List<ArtistItem> {
            return groupBy { it.artistId }.map {
                ArtistItem(id = it.key, artistName = it.value.first().artistName, albums = it.value)
            }
        }

        fun tempList(): List<AlbumItem> {
            return List(20) { index ->
                AlbumItem(
                    id = index.toLong(),
                    songs = listOf(
                        SongItem(
                            id = 1,
                            title = "tu meri muhabbat",
                            trackNumber = 1,
                            year = 2021,
                            duration = 10000,
                            data = null,
                            dateModified = 1664,
                            albumId = index.toLong(),
                            "Personal ALbum Name",
                            artistId = 6,
                            artistName = "no artist"
                        )
                    )
                )
            }
        }
    }
}


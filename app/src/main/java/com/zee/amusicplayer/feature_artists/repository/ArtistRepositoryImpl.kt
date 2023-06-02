package com.zee.amusicplayer.feature_artists.repository

import android.provider.MediaStore
import com.zee.amusicplayer.domain.model.AlbumItem.Companion.toArtists
import com.zee.amusicplayer.domain.model.ArtistItem
import com.zee.amusicplayer.domain.model.SongItem.Companion.toAlbums
import com.zee.amusicplayer.domain.repository.ArtistRepository
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.utils.Constants.ALBUM_ARTIST
import com.zee.amusicplayer.utils.SortOrder

class ArtistRepositoryImpl(private val songRepository: SongRepository) : ArtistRepository {
    override suspend fun albumArtists(): List<ArtistItem> {
        val songs = songRepository.songs(
            songRepository.makeSongCursor(
                null,
                null,
                "lower($ALBUM_ARTIST) DESC"
            )

        )
        return songs.toAlbums().toArtists()
    }

    override suspend fun albumArtists(query: String): List<ArtistItem> {
        val songs = songRepository.songs(
            songRepository.makeSongCursor(
                "$ALBUM_ARTIST LIKE ?",
                arrayOf("%$query%"), "lower($ALBUM_ARTIST) DESC"
            )

        )
        return songs.toAlbums().toArtists()
    }

    override suspend fun artists(query: String): List<ArtistItem> {
        val songs = songRepository.songs(
            songRepository.makeSongCursor(
                MediaStore.Audio.AudioColumns.ARTIST + " LIKE ?",
                arrayOf("%$query%"), "lower($ALBUM_ARTIST) DESC"
            )

        )
        return songs.toAlbums().toArtists()
    }

    override suspend fun artist(artistId: Long): ArtistItem {
        val songs = songRepository.songs(
            songRepository.makeSongCursor(
                MediaStore.Audio.AudioColumns.ARTIST_ID + "=?",
                arrayOf(artistId.toString()),
                SortOrder.SongSortOrder.SONG_A_Z
            )
        )

        return ArtistItem(
            artistId,
            artistName = songs.first().artistName,
            albums = songs.toAlbums()
        )
    }

    override suspend fun albumArtist(artistName: String): ArtistItem {
        val songs = songRepository.songs(
            songRepository.makeSongCursor(
                "album_artist" + "=?",
                arrayOf(artistName),
                SortOrder.SongSortOrder.SONG_A_Z
            )
        )
        return ArtistItem(
            id = songs.toAlbums().first().artistId,
            artistName = artistName,
            albums = songs.toAlbums()
        )
    }


}
package com.zee.amusicplayer.domain.repository

import com.zee.amusicplayer.domain.model.ArtistItem

interface ArtistRepository {
    suspend fun albumArtists(): List<ArtistItem>
    suspend fun albumArtists(query: String): List<ArtistItem>
    suspend fun artists(query: String): List<ArtistItem>
    suspend fun artist(artistId: Long): ArtistItem
    suspend fun albumArtist(artistName: String): ArtistItem
}
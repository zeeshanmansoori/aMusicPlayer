package com.zee.amusicplayer.utils

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import org.json.JSONObject

object MediaItemHelper {

    private const val ROOT_ID = "[rootID]"

    val Root = buildMediaItem(
        title = "Root Folder",
        mediaId = ROOT_ID,
        isPlayable = false,
        isBrowsable = true,
        mediaType = MediaMetadata.MEDIA_TYPE_FOLDER_MIXED
    )

    private fun buildMediaItem(
        title: String,
        mediaId: String,
        isPlayable: Boolean,
        isBrowsable: Boolean,
        mediaType: @MediaMetadata.MediaType Int,
        subtitleConfigurations: List<MediaItem.SubtitleConfiguration> = mutableListOf(),
        album: String? = null,
        artist: String? = null,
        genre: String? = null,
        sourceUri: Uri? = null,
        imageUri: Uri? = null,
    ): MediaItem {
        val metadata =
            MediaMetadata.Builder()
                .setAlbumTitle(album)
                .setTitle(title)
                .setArtist(artist)
                .setGenre(genre)
                .setIsBrowsable(isBrowsable)
                .setIsPlayable(isPlayable)
                .setArtworkUri(imageUri)
                .setMediaType(mediaType)
                // Need an empty bundle for setting up extension properties
                .setExtras(bundleOf())
                .build()

        val requestMetadata = MediaItem.RequestMetadata.Builder()
            .setMediaUri(sourceUri)
            .build()

        return MediaItem.Builder()
            .setMediaId(mediaId)
            .setSubtitleConfigurations(subtitleConfigurations)
            .setMediaMetadata(metadata)
            .setRequestMetadata(requestMetadata)
            .setUri(sourceUri)
            .build()

    }

    fun buildMediaItem(jsonObject: JSONObject): MediaItem {

        val subtitleConfigurations = buildSubTitles(jsonObject)

        return buildMediaItem(
            title = jsonObject.title,
            mediaId = jsonObject.id,
            isPlayable = true,
            isBrowsable = false,
            mediaType = MediaMetadata.MEDIA_TYPE_MUSIC,
            subtitleConfigurations,
            album = jsonObject.albumName,
            artist = jsonObject.artistName,
            genre = jsonObject.genre,
            sourceUri = Uri.parse(jsonObject.contentUri),
            imageUri = Uri.parse(jsonObject.albumCoverUri)
        ).also {
            it.dateModified = it.dateModified
        }
    }

    fun buildMediaItems(ls: List<JSONObject>): List<MediaItem> = ls.map {
        buildMediaItem(it)
    }


    private fun buildSubTitles(mediaObject: JSONObject): MutableList<MediaItem.SubtitleConfiguration> {
        val subtitleConfigurations: MutableList<MediaItem.SubtitleConfiguration> = mutableListOf()

        if (mediaObject.has("subtitles")) {
            val subtitlesJson = mediaObject.getJSONArray("subtitles")
            for (i in 0 until subtitlesJson.length()) {
                val subtitleObject = subtitlesJson.getJSONObject(i)
                subtitleConfigurations.add(
                    MediaItem.SubtitleConfiguration.Builder(Uri.parse(subtitleObject.getString("subtitle_uri")))
                        .setMimeType(subtitleObject.getString("subtitle_mime_type"))
                        .setLanguage(subtitleObject.getString("subtitle_lang"))
                        .build()
                )
            }
        }

        return subtitleConfigurations
    }
}
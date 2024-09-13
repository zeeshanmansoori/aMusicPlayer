package com.zee.amusicplayer.domain.utils

import android.net.Uri
import android.util.Log
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import org.json.JSONObject

object MediaItemHelper {

    private const val ROOT_ID = "[rootID]"
    private val catalogs = mutableMapOf<String, List<String>>()
    private val nodes = mutableMapOf<String, MediaItem>()
    private val TAG = "MediaItemHelper"
    
    val Root by lazy {

        val item = buildMediaItem(
            title = "Root Folder",
            mediaId = ROOT_ID,
            isPlayable = false,
            isBrowsable = true,
            mediaType = MediaMetadata.MEDIA_TYPE_FOLDER_MIXED
        )
        nodes[ROOT_ID] = item
        return@lazy item
    }

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
            it.dateModified = jsonObject.dateModified
        }
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

    fun addChildren(parentId: String, mediaItems: List<MediaItem>) {
        catalogs[parentId] = mediaItems.map {
            nodes[it.mediaId] = it
            it.mediaId
        }
        Log.d(TAG, "addChildren: parentId $parentId size ${mediaItems.size}")
    }

    fun getChildren(parentId: String): List<MediaItem> {
        val ids = catalogs[parentId]
        Log.d(TAG, "getChildren: parentId $parentId ids $ids")
        return ids?.map {
            nodes[it]!!
        }?: emptyList()
    }

    fun getChild(mediaId: String): MediaItem {
        Log.d(TAG, "getChild: mediaId $mediaId child ${nodes[mediaId]}")
        return nodes[mediaId]!!
    }
}
package com.zee.amusicplayer.exo_player

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMusicSource(private val songRepository: SongRepository) : AbstractMusicSource() {

    var songList: List<MediaMetadataCompat> = emptyList()


    override suspend fun load() {
        state = MusicSourceState.STATE_INITIALIZING
        try {

            val songs = withContext(Dispatchers.IO) {
                songRepository.songs()
            }



            songList = songs.map { songItem ->

                MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songItem.artistName)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, songItem.id.toString())
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songItem.title)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, songItem.title)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, songItem.albumUri)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, songItem.contentUri)
                    .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, songItem.albumUri)
                    .putString(
                        MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,
                        songItem.artistName
                    )
                    .putString(
                        MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION,
                        songItem.data
                    )
                    .build()
            }


            state = MusicSourceState.STATE_INITIALIZED

        } catch (e: Exception) {
            log("Local Music Source ", error = e)
            state = MusicSourceState.STATE_ERROR
        }
    }

    fun asMediaSource(dataSourceFactory: DataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        songList.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)))

            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItems() = songList.map { song ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(song.description.title)
            .setSubtitle(song.description.subtitle)
            .setMediaId(song.description.mediaId)
            .setIconUri(song.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
    }.toMutableList()


//    override fun iterator(): Iterator<MediaMetadataCompat> {
//        return songList.iterator()
//    }
}
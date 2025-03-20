package com.zee.amusicplayer.ui.home.components

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.ui.common.MusicImage
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.DropDownOption

@Composable
fun SongItemUi(
    modifier: Modifier = Modifier,
    song: Song,
    showEqualizer: Boolean = false,
) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Constants.rectanglesCorner))
                .background(color = Color.LightGray),
            artUri = song.artUri,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            Text(
                text = song.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = song.artistName,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (showEqualizer)
            EqualizerLoader()

        IconButton(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(20.dp), onClick = { expanded = !expanded }) {
            Icon(painter = painterResource(id = R.drawable.ic_more_vert), contentDescription = null)

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val context = LocalContext.current

                with(DropDownOption.Delete) {
                    if (isEnabled) {

                        val deleteMediaLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult()
                        ) { result ->
                            Log.d("zeeshan", "SongItemUi: called ${result.resultCode}")
                            if (result.resultCode == Activity.RESULT_OK) {
                                deleteSong(song, context)
                                expanded = false
                            }
                        }
                        DropdownMenuItem(
                            onClick = {
                                val deleted = deleteSong(song, context) { request ->
                                    Log.d("zeeshan", "SongItemUi: request $request")
                                    deleteMediaLauncher.launch(request)
                                }
                                if (deleted)
                                    expanded = false
                            },
                            text = { Text(name, style = MaterialTheme.typography.bodyLarge) },
                        )
                    }
                }


                with(DropDownOption.PlayNext) {
                    if (isEnabled)
                        DropdownMenuItem(
                            onClick = {
                                expanded = false

                            },
                            text = { Text(name, style = MaterialTheme.typography.bodyLarge) }
                        )
                }


            }
        }


    }

}



package com.zee.amusicplayer.presentation.player_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.presentation.player_screen.component.BottomControllerBar
import com.zee.amusicplayer.presentation.player_screen.component.PlayerControllerBar
import com.zee.amusicplayer.presentation.player_screen.component.TrackBar
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText

@ExperimentalMaterialApi
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    visiblity: Float = 1f
) {

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier
                .fillMaxSize()
                .alpha(visiblity),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))

            ) {

                Image(
                    modifier = modifier
                        .background(color = Color.LightGray)
                        .padding(80.dp),
                    painter = painterResource(id = R.drawable.ic_songs),
                    contentDescription = null
                )

            }

            TrackBar(modifier = Modifier.padding(vertical = 10.dp))
            MarqueeText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                text = "song that is playing right now ksnfksd sfbfibsfd sdjbsdf ",
                style = MaterialTheme.typography.subtitle2
            )

            Text(text = "album name", Modifier.padding(vertical = 30.dp))

            PlayerControllerBar()

        }

        BottomControllerBar(modifier = Modifier.align(Alignment.BottomCenter))

    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PlayerScreenPreview() {
    PlayerScreen()
}
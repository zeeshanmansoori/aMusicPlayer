package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R

@Composable
fun HomeScreenListContainer(modifier: Modifier = Modifier, containerTitle: String = "") {
    Column(modifier = modifier.fillMaxWidth()) {


        Row(
            Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .clickable {

                }
                .padding(vertical = 5.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = containerTitle, style = MaterialTheme.typography.h5)
            Icon(painter = painterResource(id = R.drawable.ic_next), contentDescription = null)

        }


        LazyRow(Modifier.fillMaxWidth()) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenListContainerPreview() {
    HomeScreenListContainer()
}
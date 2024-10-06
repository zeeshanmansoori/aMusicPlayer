package com.zee.amusicplayer.ui.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.main.components.AMusicAppBar
import com.zee.amusicplayer.ui.main.components.AppNameUi
import com.zee.amusicplayer.ui.theme.IconTintColor

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
) {

    var searchStarted by remember {
        mutableStateOf(false)
    }

    var search by remember { mutableStateOf("") }

    AMusicAppBar(modifier = modifier) {
        if (searchStarted) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = search,
                onValueChange = {
                    search = it
                },
                trailingIcon = {
                    IconButton(onClick = {
                        searchStarted = false
                    }) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "",
                        )
                    }
                }
            )
            return@AMusicAppBar
        }


        AppNameUi(modifier = Modifier.align(Alignment.Center))
        IconButton(onClick = {
            searchStarted = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_serch),
                contentDescription = "Settings",
                tint = IconTintColor
            )
        }

    }
}
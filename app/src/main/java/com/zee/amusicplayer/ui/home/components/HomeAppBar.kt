package com.zee.amusicplayer.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.main.components.AMusicAppBar
import com.zee.amusicplayer.ui.main.components.AppNameUi
import com.zee.amusicplayer.ui.theme.IconTintColor

@Composable
fun HomeAppBar(
    filterKey: String,
    onFilterKeyChanged: (String) -> Unit,
    isSearchVisible: Boolean,
    changeSearchVisibility: (Boolean) -> Unit,
) {

    AMusicAppBar(modifier = Modifier) {
        if (isSearchVisible) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = filterKey,
                onValueChange = onFilterKeyChanged,
                placeholder = {
                    Text("Type here to search ...")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        changeSearchVisibility(false)
                        onFilterKeyChanged.invoke("")
                    }) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "",
                        )
                    }
                },
                singleLine = true,
                colors =
                TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface
                )
            )
            return@AMusicAppBar
        }


        AppNameUi(modifier = Modifier.align(Alignment.Center))
        IconButton(onClick = {
            changeSearchVisibility(true)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_serch),
                contentDescription = "Settings",
                tint = IconTintColor
            )
        }

    }
}
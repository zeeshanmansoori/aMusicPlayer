package com.zee.amusicplayer.ui.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.main.components.AMusicAppBar
import com.zee.amusicplayer.ui.main.components.AppNameUi


@Composable
fun HomeAppBar(
    onFilterKeyChanged: (String) -> Unit,
    isSearchVisible: Boolean,
    changeSearchVisibility: (Boolean) -> Unit,
) {

    val focusRequester = remember {
        FocusRequester()
    }

    val input = remember {
        mutableStateOf("")
    }

    AMusicAppBar(modifier = Modifier) {
        if (isSearchVisible) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = input.value,
                onValueChange = {
                    input.value = it
                    onFilterKeyChanged.invoke(it)
                },
                placeholder = {
                    Text("Type here to search ...")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        changeSearchVisibility(false)
                    }) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "",
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
//                    backgroundColor = MaterialTheme.colorScheme.surface
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
            )
        }

    }

    LaunchedEffect(key1 = focusRequester, key2 = isSearchVisible) {
        if (isSearchVisible) focusRequester.requestFocus()
        else input.value = ""
    }
}
@file:OptIn(ExperimentalPermissionsApi::class)

package com.zee.amusicplayer.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.zee.amusicplayer.ui.main.MainViewModel
import com.zee.amusicplayer.ui.main.MusicApp
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val permissions = mutableListOf<String>()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissions.add(android.Manifest.permission.READ_MEDIA_AUDIO)
                        permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                    val permissionState =
                        rememberMultiplePermissionsState(permissions = permissions) {
                            // permissions are granted, now we can trigger the fetching again.
                            viewModel.triggerFetchMusicWorker()

                        }

                    if (!permissionState.allPermissionsGranted) {
                        PermissionNotGranted {
                            permissionState.launchMultiplePermissionRequest()
                        }
                        return@Surface
                    }

                    MusicApp(viewModel = viewModel)

                }
            }
        }
    }


    @Composable
    fun PermissionNotGranted(retryBtn: () -> Unit) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Need Storage permission in order to play music",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = retryBtn) {
                Text(
                    "Grant Permission",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

    }
}
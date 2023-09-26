package com.zee.amusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.zee.amusicplayer.presentation.home.HomeScreen
import com.zee.amusicplayer.presentation.home.HomeViewModel
import com.zee.amusicplayer.presentation.theme.AMusicPlayerTheme

@UnstableApi
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {
                // A surface container using the 'background' color from the theme

                val mediaItems = viewModel.items.collectAsState()
                val currentIndex = viewModel.currentIndex.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    HomeScreen(items = mediaItems.value,currentIndex =currentIndex.value ) { _, index ->
                        viewModel.onItemClick(index)
                    }
                }
            }
        }
    }
}
package com.zee.amusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.zee.amusicplayer.presentation.main.MainScreen
import com.zee.amusicplayer.presentation.main.MainViewModel
import com.zee.amusicplayer.presentation.theme.AMusicPlayerTheme

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
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
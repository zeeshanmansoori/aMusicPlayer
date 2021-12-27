package com.zee.amusicplayer.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zee.amusicplayer.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainVieModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    val currentScreen = mutableStateOf<Screen>(Screen.HomeScreen)

    fun updateScreen(screen: Screen) {
        currentScreen.value = screen
    }
}
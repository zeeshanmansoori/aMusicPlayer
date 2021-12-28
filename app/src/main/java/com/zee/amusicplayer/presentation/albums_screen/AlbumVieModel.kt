package com.zee.amusicplayer.presentation.albums_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zee.amusicplayer.domain.model.AlbumItem
import com.zee.amusicplayer.domain.use_cases.GetAllAlbumUseCase
import com.zee.amusicplayer.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumVieModel @Inject constructor(private val useCase: GetAllAlbumUseCase) : ViewModel() {


    private val _allAlbums = mutableStateOf(listOf<AlbumItem>())
    val allAlbums: State<List<AlbumItem>> = _allAlbums

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allAlbums.value = useCase.invoke()
            } catch (e: Exception) {
                log("e $e")
            }
        }

    }

}
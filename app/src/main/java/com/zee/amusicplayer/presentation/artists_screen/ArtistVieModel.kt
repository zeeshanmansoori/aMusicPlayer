package com.zee.amusicplayer.presentation.artists_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zee.amusicplayer.domain.model.ArtistItem
import com.zee.amusicplayer.domain.use_cases.GetAllArtistUseCase
import com.zee.amusicplayer.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistVieModel @Inject constructor(private val useCase: GetAllArtistUseCase) : ViewModel() {


    private val _allArtist = mutableStateOf(listOf<ArtistItem>())
    val allArtist: State<List<ArtistItem>> = _allArtist

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allArtist.value = useCase.invoke()
            } catch (e: Exception) {
                log("e $e")
            }
        }

    }

}
package com.zee.amusicplayer.presentation.artists_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zee.amusicplayer.domain.model.ArtistItem
import com.zee.amusicplayer.domain.use_cases.GetAllArtistUseCase
import com.zee.amusicplayer.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistVieModel @Inject constructor(private val useCase: GetAllArtistUseCase) : ViewModel() {


    private val _allArtist = MutableStateFlow(listOf<ArtistItem>())
    val allArtist = _allArtist.asStateFlow()

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
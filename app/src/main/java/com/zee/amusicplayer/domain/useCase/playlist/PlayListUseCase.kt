package com.zee.amusicplayer.domain.useCase.playlist

import com.zee.amusicplayer.domain.model.PlayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayListUseCase(val scope: CoroutineScope) {
    private val _playList = MutableStateFlow<List<PlayList>>(emptyList())
    val playList = _playList.asStateFlow()


    fun addNewPlayList(name: String) {
        val playList = PlayList(name = name, emptyList())
        val list = _playList.value.toMutableList()
        list.add(playList)

        _playList.value = list
    }
}
package com.zee.amusicplayer.domain.useCase.sorting

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale

class SortByNameUseCase<T> {

    operator fun invoke(list: List<T>, lambda: (T) -> String): List<T> {
        return list.sortedBy {
            lambda(it).capitalize(Locale.current)
        }
    }
}
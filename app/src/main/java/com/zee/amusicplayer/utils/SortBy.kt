package com.zee.amusicplayer.utils

import com.zee.amusicplayer.di.AppModule
import com.zee.amusicplayer.domain.model.Song

sealed class SortBy {
    abstract fun sortList(items: List<Song>): List<Song>

    data object Name : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val useCase = AppModule.provideSortByNameUseCase<Song>()
            return useCase(items) {
                it.title
            }
        }
    }

    data object History : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.sortByDescending {
                it.lastPlayedDate
            }

            return list
        }
    }

    data object LastAdded : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.sortByDescending {

                it.mediaItem.dateModified
            }

            return list
        }
    }

    data object MostPlayed : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.sortByDescending {
                it.playedCount
            }

            return list
        }
    }

    data object Shuffle : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.shuffle()

            return list
        }
    }


}
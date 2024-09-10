package com.zee.amusicplayer.domain.utils


import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.utils.dateModified

sealed class SortBy {
    abstract fun sortList(items: List<Song>): List<Song>

    data object Name : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.sortBy {
                it.title
            }

            return list
        }
    }

    data object History : SortBy() {
        override fun sortList(items: List<Song>): List<Song> {
            val list = items.toMutableList()
            list.sortBy {
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
            list.sortBy {
                it.mostPlayedCount
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
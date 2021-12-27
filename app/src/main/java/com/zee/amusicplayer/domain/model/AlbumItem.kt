package com.zee.amusicplayer.domain.model

data class AlbumItem(
    val albumName: String,
    val albumIcon: Int? = null,
    val ls: List<SongItem> = emptyList()
) {
    companion object {
        fun tempList(): List<AlbumItem> {
            return listOf(
                AlbumItem("Islamic naat"),
                AlbumItem("Gene Maclellin"),
                AlbumItem("Mohammed Tarek"),
                AlbumItem("Who"),
                AlbumItem("Naat"),
                AlbumItem("YMusic"),
                AlbumItem("Islamic naat"),
                AlbumItem("Gene Maclellin"),
                AlbumItem("Mohammed Tarek"),
                AlbumItem("Who"),
                AlbumItem("Naat"),
                AlbumItem("YMusic"),
            )
        }
    }
}


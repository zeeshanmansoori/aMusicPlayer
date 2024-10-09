package com.zee.amusicplayer.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.ui.home.components.HomeAppBar
import com.zee.amusicplayer.ui.home.components.HomeActionBar
import com.zee.amusicplayer.ui.home.components.SongItemUi
import com.zee.amusicplayer.ui.main.MainViewModel
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    bottomSheetState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
) {

    val playerState = viewModel.playerState.collectAsState()
    val selectedSortState = viewModel.sortByE.collectAsState()
    val songsState = viewModel.songsState.collectAsState()
    val isSearchVisibleState = viewModel.isSearchVisible.collectAsState()
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()

    val songs = songsState.value.songs
    val currentSong = playerState.value.item

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeAppBar(
            onFilterKeyChanged = viewModel::onFilterKeyChanged,
            isSearchVisible = isSearchVisibleState.value,
            changeSearchVisibility = viewModel::changeSearchVisibility,
        )
        HomeActionBar(
            selectedSortBy = selectedSortState.value,
            onSortActionChange = {
                viewModel.onSortActionChange(it)
                scope.launch {
                    state.scrollToItem(0, 0)
                }
            },
        )

        if (songsState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = Constants.SIDE_PADDING),
            state = state,
        ) {

            itemsIndexed(songs, key = { _, b ->
                b.id
            }) { itemPosition, item ->
                SongItemUi(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(Constants.rectanglesCorner))
                        .clickable {
                            val bottomSheetCollapsed =
                                bottomSheetState.bottomSheetState.isCollapsed
                            if (bottomSheetCollapsed) scope.launch {
                                bottomSheetState.bottomSheetState.expand()
                            }
                            viewModel.onItemClick(itemPosition)
                        }
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    song = item,
                    showEqualizer = item.id == currentSong?.id,
                )
            }
        }

    }

}

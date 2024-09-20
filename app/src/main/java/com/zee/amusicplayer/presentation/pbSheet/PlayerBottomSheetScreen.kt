package com.zee.amusicplayer.presentation.pbSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.zee.amusicplayer.presentation.main.MainViewModel
import com.zee.amusicplayer.presentation.pbSheet.component.PlayerBottomSheetContent
import com.zee.amusicplayer.presentation.pbSheet.component.PlayerCollapseBar
import com.zee.amusicplayer.utils.currentFraction

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PlayerBottomSheetScreen(
    bottomSheetState: BottomSheetScaffoldState,
    viewModel: MainViewModel,
) {

    val playerState = viewModel.playerState.collectAsState()
    Column(Modifier.fillMaxSize()) {
        PlayerCollapseBar(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .alpha(1 - bottomSheetState.currentFraction),
            playerState = playerState.value,
            onPlayPauseClick = viewModel::onPlayPauseClick
        )
        PlayerBottomSheetContent(
            modifier = Modifier,
            visibility = bottomSheetState.currentFraction,
            playerState = playerState.value,
            onPlayPauseClick  =viewModel::onPlayPauseClick,
            onNextButtonClick = viewModel::onPlayNextClick,
            onPreviousButtonClick =viewModel::onPreviousButtonClick,
            seekTo = viewModel::onSeekToClick
        )

    }
}
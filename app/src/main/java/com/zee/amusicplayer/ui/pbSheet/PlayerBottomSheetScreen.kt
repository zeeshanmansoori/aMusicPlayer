package com.zee.amusicplayer.ui.pbSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.zee.amusicplayer.ui.main.MainViewModel
import com.zee.amusicplayer.ui.pbSheet.component.PlayerBottomSheetContent
import com.zee.amusicplayer.ui.pbSheet.component.PlayerCollapseBar
import kotlinx.coroutines.launch

@OptIn( ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheetScreen(
    bottomSheetState: BottomSheetScaffoldState,
    viewModel: MainViewModel,
) {

    val playerState = viewModel.playerState.collectAsState()
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        PlayerCollapseBar(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .alpha(1 - 0f),
            playerState = playerState.value,
            onPlayPauseClick = viewModel::onPlayPauseClick,
            onHeaderClicked = {
                val bottomSheetCollapsed =
                    bottomSheetState.bottomSheetState.isVisible
                if (bottomSheetCollapsed) scope.launch {
                    bottomSheetState.bottomSheetState.expand()
                }
            }
        )
        PlayerBottomSheetContent(
            modifier = Modifier,
            visibility = 1f,
            playerState = playerState.value,
            onPlayPauseClick  =viewModel::onPlayPauseClick,
            onNextButtonClick = viewModel::onPlayNextClick,
            onPreviousButtonClick =viewModel::onPreviousButtonClick,
            seekTo = viewModel::onSeekToClick
        )

    }
}
package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenSuggestion(
    modifier: Modifier = Modifier,

    ) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Suggestions", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(10.dp))


    }
}

@Composable
fun SingleSuggestionItem() {

}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuggestionPreview() {
    HomeScreenSuggestion()
}

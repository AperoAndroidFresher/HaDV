package com.example.dovietha_bt.myplaylist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent


@Composable
fun ColumnList(list: List<Music> = listOf(),viewModel: MyPlaylistViewModel = viewModel()) {
    LazyColumn(
        Modifier.Companion
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { item ->
            MusicItemColumn(
                image = item.image,
                name = item.name,
                author = item.author,
                duration = item.duration,
                onOptionClick = {
                    viewModel.processIntent(MyPlaylistIntent.RemoveSong(item))
                }
            )
        }
    }
}
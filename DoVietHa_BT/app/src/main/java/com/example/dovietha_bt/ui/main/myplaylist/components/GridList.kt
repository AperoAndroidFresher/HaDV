package com.example.dovietha_bt.ui.main.myplaylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent

@Composable
fun GridList(
    list: List<MusicVM> = listOf(),
    viewModel: MyPlaylistViewModel = viewModel(),
    option: List<Option> = emptyList()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(list) { item ->
            MusicItemGrid(
                image = item.image,
                item.name,
                item.author,
                item.duration,
                onItemClick = {
                    viewModel.processIntent(MyPlaylistIntent.RemoveSong(item.id, 0))
                },
                option = option
            )
        }
    }
}
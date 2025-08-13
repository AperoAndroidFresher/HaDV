package com.example.dovietha_bt.ui.main.myplaylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.common.getEmbeddedImageBytes
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent

@Composable
fun GridList(
    list: List<MusicVM> = listOf(),
    option: List<Option> = emptyList(),
    onOptionClick: (Option, MusicVM) -> Unit,
    onItemClick:(Int) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
    ) {
        items(list) { item ->
            MusicItemGrid(
                image = getEmbeddedImageBytes(item.path),
                name = item.name,
                author = item.author,
                duration = item.duration,
                onOptionClick = { onOptionClick(it, item) },
                option = option,
                onItemClick = {onItemClick(list.indexOf(item))}
            )
        }
    }
}

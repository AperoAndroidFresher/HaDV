package com.example.dovietha_bt.my_playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.my_playlist.model.Music
import com.example.dovietha_bt.my_playlist.utils.removeByOption

@Composable
fun GridList(list: MutableList<Music> = mutableListOf()) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(list) { item ->
            MusicItemGrid(
                item.image,
                item.name,
                item.author,
                item.duration,
                onItemClick = {
                    list.removeByOption(it, item)
                }
            )
        }
    }
}
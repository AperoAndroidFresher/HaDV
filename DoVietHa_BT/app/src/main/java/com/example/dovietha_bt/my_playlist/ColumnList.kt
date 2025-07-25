package com.example.dovietha_bt.my_playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.my_playlist.model.Music
import com.example.dovietha_bt.my_playlist.utils.removeByOption

@Composable
fun ColumnList(list: MutableList<Music> = mutableListOf()) {
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
                onItemClick = {
                    list.removeByOption(it, item)
                }
            )
        }
    }
}
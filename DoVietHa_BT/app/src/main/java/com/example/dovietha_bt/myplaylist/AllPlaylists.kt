package com.example.dovietha_bt.myplaylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.Option
import com.example.dovietha_bt.myplaylist.model.Playlist
import com.example.dovietha_bt.myplaylist.view.MusicItemColumn

@Composable
fun AllPlaylists(
    list:List<Playlist> = emptyList(),
    option: List<Option> = emptyList(),
    onOptionClick: (Option, Playlist) -> Unit,
    onClick: (List<Music>) -> Unit
){
    LazyColumn(
        Modifier.Companion
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { item ->
            PlaylistItemColumn(
                name = item.name,
                sumSongs = item.musics.size,
                onOptionClick = { onOptionClick(it, item) },
                option = option,
                onClick = {onClick(item.musics)}
            )
        }
    }
}
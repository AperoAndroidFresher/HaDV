package com.example.dovietha_bt.myplaylist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.login.LoginScreenViewModel
import com.example.dovietha_bt.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.myplaylist.model.Music
import com.example.dovietha_bt.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.myplaylist.model.Option

@Composable
fun GridList(list: List<Music> = listOf(), viewModel: MyPlaylistViewModel = viewModel(),option: List<Option> = emptyList()) {
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
                    viewModel.processIntent(MyPlaylistIntent.RemoveSong(item,0))
                },
                option = option
            )
        }
    }
}
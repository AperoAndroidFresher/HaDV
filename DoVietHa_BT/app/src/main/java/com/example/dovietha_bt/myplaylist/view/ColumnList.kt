package com.example.dovietha_bt.myplaylist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.myplaylist.model.MusicVM
import com.example.dovietha_bt.myplaylist.model.Option


@Composable
fun ColumnList(
    list: List<MusicVM> = listOf(),
    option: List<Option> = emptyList(),
    onOptionClick: (Option, MusicVM) -> Unit
) {
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
                onOptionClick = { onOptionClick(it, item) },
                option = option
            )
        }
    }
}
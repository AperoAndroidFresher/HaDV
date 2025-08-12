package com.example.dovietha_bt.ui.main.myplaylist.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.dovietha_bt.MusicService
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM


@Composable
fun ColumnList(
    list: List<MusicVM> = listOf(),
    option: List<Option> = emptyList(),
    onOptionClick: (Option, MusicVM) -> Unit,
    onItemClick:(Int) -> Unit = {}
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
                uri = item.path.toUri(),
                option = option,
                onOptionClick = { onOptionClick(it, item) },
                onItemClick = { onItemClick(list.indexOf(item)) },
            )
        }
    }
}


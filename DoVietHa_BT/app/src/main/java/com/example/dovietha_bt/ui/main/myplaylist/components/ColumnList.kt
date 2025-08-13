package com.example.dovietha_bt.ui.main.myplaylist.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.dovietha_bt.MusicService
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.common.getEmbeddedImageBytes
import com.example.dovietha_bt.rememberDragDropListState
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ColumnList(
    list: List<MusicVM> = listOf(),
    option: List<Option> = emptyList(),
    onOptionClick: (Option, MusicVM) -> Unit,
    onItemClick:(Int) -> Unit = {},
    onMove: (Int, Int) -> Unit ={_,_-> },
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)
    LazyColumn(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset = offset)

                        if (overScrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overScrollJob = scope.launch {
                                    dragDropListState.lazyListState.scrollBy(it)
                                }
                            } ?: kotlin.run { overScrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() },
                )
            }
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = dragDropListState.lazyListState,
    ) {
        items(list.size) { index ->
            MusicItemColumn(
                image = getEmbeddedImageBytes(list[index].path),
                name = list[index].name,
                author = list[index].author,
                duration = list[index].duration,
                option = option,
                onOptionClick = { onOptionClick(it, list[index]) },
                onItemClick = { onItemClick(list.indexOf(list[index])) },
                modifier = Modifier.composed {
                    val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                        index == dragDropListState.currentIndexOfDraggedItem
                    }
                    Modifier.graphicsLayer {
                        translationY = offsetOrNull ?: 0f
                    }
                }
            )
        }
    }
}


package com.example.dovietha_bt.ui.main.myplaylist.mymusic

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.MusicPlayerService
import com.example.dovietha_bt.MusicServiceConnectionHelper
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.move
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM
import com.example.dovietha_bt.ui.main.myplaylist.components.ColumnList
import com.example.dovietha_bt.ui.main.myplaylist.components.GridList
import kotlinx.coroutines.delay

val options = listOf(
    Option(R.drawable.ic_remove, "Remove from playlist"),
    Option(R.drawable.ic_share, "Share (Coming soon)")
)

@Composable
fun MyMusicScreen(
    viewModel: MyMusicScreenViewModel = viewModel(),
    playlist: PlaylistVM = PlaylistVM(),
    isCloseBar:(Boolean) ->Unit = {}
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    
    var isSort by remember { mutableStateOf(false) }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isViewChange by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.processIntent(MyMusicIntent.LoadSongs(playlist.id))
        MusicServiceConnectionHelper.bind(context)

        // Theo dõi thay đổi index từ service
        var lastIndex = -1
        while (true) {
            val newIndex = MusicServiceConnectionHelper.musicService?.getCurrentIndex() ?: 0
            if (newIndex != lastIndex) {
                lastIndex = newIndex
                currentIndex = newIndex // <- cập nhật State, khiến LaunchedEffect bên dưới chạy lại
            }
            delay(300) // kiểm tra mỗi 300ms
        }
    }

    LaunchedEffect(currentIndex) {
        if (currentIndex in playlist.musics.indices) {
            viewModel.processIntent(MyMusicIntent.CurrentSong(playlist.musics[currentIndex]))
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                "My Playlist",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp,
                fontWeight = Bold
            )
            Row(
                Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(if (isViewChange) R.drawable.ic_view else R.drawable.ic_list),
                    "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(onClick = { isViewChange = !isViewChange })
                )
                Spacer(Modifier.padding(8.dp))
                IconButton(onClick = {isSort=!isSort}){
                    Icon(
                        painterResource(if(!isSort) R.drawable.ic_sort_up else R.drawable.ic_check),
                        "",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(26.dp),
                    )
                }
            }
        }
        if (isViewChange) {
            GridList(
                list = state.value.listSong,
                option = options,
                onOptionClick = { option, music ->
                    if (option.desc == "Remove from playlist") {
                        viewModel.processIntent(MyMusicIntent.RemoveSong(music.id, playlist.id))
                    }
                },
                onItemClick = { index ->
                    startMusicServiceWithIndex(
                        index = index,
                        context = context,
                        songList = state.value.listSong
                    )
                    isCloseBar(false)
                }
            )
        } else {
            ColumnList(
                list = state.value.listSong,
                option = options,
                onOptionClick = { option, music ->
                    if (option.desc == "Remove from playlist") {
                        viewModel.processIntent(MyMusicIntent.RemoveSong(music.id, playlist.id))
                    }
                },
                onItemClick = { index ->
                    startMusicServiceWithIndex(
                        index = index,
                        context = context,
                        songList = state.value.listSong
                    )
                    isCloseBar(false)
                },
                onMove = { from, to -> viewModel.processIntent(MyMusicIntent.MoveSong(from, to))},
                isEditMode = isSort
            )
        }
    }
}

fun startMusicServiceWithIndex(index: Int, context: Context, songList: List<MusicVM>) {
    val intent = Intent(context, MusicPlayerService::class.java).apply {
        action = MusicPlayerService.ACTION_PLAY
        putParcelableArrayListExtra("MUSIC_LIST", ArrayList(songList))
        putExtra("CURRENT_INDEX", index)
    }
    ContextCompat.startForegroundService(context, intent)
}



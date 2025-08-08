package com.example.dovietha_bt.ui.main.myplaylist.components

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM

val options = listOf(
    Option(R.drawable.ic_remove, "Remove from playlist"),
    Option(R.drawable.ic_share, "Share (Coming soon)")
)

@Composable
fun MyMusicScreen(
    viewModel: MyPlaylistViewModel = viewModel(),
    playlist: PlaylistVM = PlaylistVM()
) {
    val state = viewModel.state.collectAsState()
    
    val currentList = state.value.playlists.find {
        Log.d("CURRENT LIST","${playlist}")
        it.id == playlist.id
    } ?: return

    LaunchedEffect(Unit) {
        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists)
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
                    painterResource(if (!state.value.isViewChange) R.drawable.ic_view else R.drawable.ic_list),
                    "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(onClick = { viewModel.processIntent(MyPlaylistIntent.ToggleView) })
                )
                Spacer(Modifier.padding(8.dp))
                Icon(
                    painterResource(R.drawable.ic_sort_up),
                    "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        if (state.value.isViewChange) {
            GridList(currentList.musics, viewModel, options)
        } else {
            ColumnList(
                list = currentList.musics,
                option = options,
                onOptionClick = { option, music ->
                    if (option.desc == "Remove from playlist") {
                        viewModel.processIntent(
                            MyPlaylistIntent.RemoveSong(music.id, currentList.id)
                        )
                        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists)
                    }
                }
            )
        }
    }
}


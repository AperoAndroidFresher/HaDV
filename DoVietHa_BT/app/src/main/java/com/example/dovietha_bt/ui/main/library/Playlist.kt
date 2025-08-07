package com.example.dovietha_bt.ui.main.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.ui.main.myplaylist.components.PlaylistItemColumn
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM
import com.example.dovietha_bt.ui.main.myplaylist.components.ColumnList

val libOptions = listOf(
    Option(R.drawable.ic_remove, "Add to playlist"),
    Option(R.drawable.ic_share, "Share (Coming soon)")
)

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(),
    onAddClicked: () -> Unit = {},
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event
    var musicAdded by remember { mutableStateOf(MusicVM()) }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadPlaylists)
        viewModel.processIntent(LibraryIntent.LoadSong)
        event.collect { event ->
            when (event) {
                LibraryEvent.ShowDialog -> showDialog = true
            }
        }
    }
    Box {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Library",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Row {
                    Button(
                        onClick = {
                            viewModel.processIntent(LibraryIntent.LoadSong)
                        }
                    ) {
                        Text("Local")
                    }

                    Spacer(Modifier.padding(8.dp))

                    Button(
                        onClick = {
                            viewModel.processIntent(LibraryIntent.ShowRemote)
                        }
                    ) {
                        Text("Remote")
                    }
                }
                if(state.value.isLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x88000000)),
                        contentAlignment = Alignment.Center
                    ){
                        LottieAnimationLoading()
                    }
                }
                else if(state.value.isDisconnect){
                    DisconnectRemote { viewModel.processIntent(LibraryIntent.ShowRemote) }
                }
                else{
                    ColumnList(
                        list = state.value.musics,
                        option = libOptions,
                        onOptionClick = { option, music ->
                            if (option.desc == "Add to playlist") {
                                showDialog = true
                                musicAdded = music
                            }
                        }
                    )
                }
            }
        }
        if (showDialog) {
            AddDialog(
                playlistList = state.value.playlists,
                onDismissRequest = { showDialog = false },
                modifier = Modifier.align(Alignment.Center),
                onAddClicked = onAddClicked,
                onPlaylistClick = {
                    viewModel.processIntent(LibraryIntent.AddToPlaylist(musicAdded, it.id))
                    viewModel.processIntent(LibraryIntent.LoadPlaylists)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddDialog(
    modifier: Modifier = Modifier,
    playlistList: List<PlaylistVM> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onAddClicked: () -> Unit = {},
    onPlaylistClick: (PlaylistVM) -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .size(350.dp, 440.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (playlistList.isEmpty()) AddCase(onAddClicked)
            else PlaylistCase(playlistList, onClick = onPlaylistClick)
        }
    }
}

@Composable
fun AddCase(onAddClicked: () -> Unit = {}) {
    Text("Choose playlist")
    Text("You don't have any playlists. Click the \"+\" button to add")
    OutlinedButton(
        onClick = onAddClicked,
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Icon(painterResource(R.drawable.ic_add), "")
    }
}

@Composable
fun PlaylistCase(list: List<PlaylistVM> = emptyList(), onClick: (PlaylistVM) -> Unit = {}) {
    LazyColumn {
        items(list) { playlist ->
            PlaylistItemColumn(
                name = playlist.name,
                sumSongs = playlist.musics.size,
                onClick = { onClick(playlist) }
            )
        }
    }
}
@Composable
fun LottieAnimationLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/lottie_remote_item_loading.json"))
    val progress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition, progress = { progress }, modifier = Modifier.size(100.dp)
    )
}
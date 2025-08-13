package com.example.dovietha_bt.ui.main.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.common.UserInformation
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM
import com.example.dovietha_bt.ui.main.myplaylist.components.ColumnList
import com.example.dovietha_bt.ui.main.myplaylist.components.PlaylistItemColumn

val libOptions = listOf(
    Option(R.drawable.ic_add_music, "Add to playlist"),
    Option(R.drawable.ic_share, "Share (Coming soon)"),
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
        viewModel.processIntent(LibraryIntent.LoadPlaylists(UserInformation.username))
        viewModel.processIntent(LibraryIntent.LoadLocalSong)
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
                .padding(16.dp),
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Library",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
                Row {
                    Button(
                        onClick = {
                            viewModel.processIntent(LibraryIntent.LoadLocalSong)
                        },
                    ) {
                        Text("Local")
                    }

                    Spacer(Modifier.padding(8.dp))

                    Button(
                        onClick = {
                            viewModel.processIntent(LibraryIntent.LoadRemoteSong)
                        },
                    ) {
                        Text("Remote")
                    }
                }
                if (state.value.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ) {
                        LottieAnimationLoading()
                    }
                } else if (state.value.canLoadMusic) {
                    DisconnectRemote { viewModel.processIntent(LibraryIntent.LoadRemoteSong) }
                } else {
                    ColumnList(
                        list = state.value.musics,
                        option = libOptions,
                        onOptionClick = { option, music ->
                            // Log.d("LIBRARY DEBUG","${music}")
                            if (option.desc == "Add to playlist") {
                                showDialog = true
                                musicAdded = music
                            }
                        },
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
                    viewModel.processIntent(LibraryIntent.LoadPlaylists(UserInformation.username))
                    showDialog = false
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddDialog(
    modifier: Modifier = Modifier,
    playlistList: List<PlaylistVM> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onAddClicked: () -> Unit = {},
    onPlaylistClick: (PlaylistVM) -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .size(350.dp, 400.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Choose playlist",
                fontWeight = Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(8.dp))
            if (playlistList.isEmpty()) AddCase(onAddClicked)
            else PlaylistCase(playlistList, onClick = onPlaylistClick)
        }
    }
}

@Composable
fun AddCase(onAddClicked: () -> Unit = {}) {
    Spacer(modifier = Modifier.height(62.dp))
    Text(
        text = "You don't have any playlists. Click the \"+\" button to add",
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 80.dp),
    )
    OutlinedButton(
        onClick = onAddClicked,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .padding(24.dp)
            .size(80.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun PlaylistCase(list: List<PlaylistVM> = emptyList(), onClick: (PlaylistVM) -> Unit = {}) {
    LazyColumn(modifier = Modifier.padding(8.dp),
               verticalArrangement = Arrangement.SpaceBetween) {
        items(list) { playlist ->
            PlaylistItemColumn(
                name = playlist.name,
                sumSongs = playlist.musics.size,
                onClick = { onClick(playlist) },
                haveOption = false
            )
        }
    }
}

@Composable
fun LottieAnimationLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/lottie_remote_item_loading.json"))
    val progress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition, progress = { progress }, modifier = Modifier.size(100.dp),
    )
}


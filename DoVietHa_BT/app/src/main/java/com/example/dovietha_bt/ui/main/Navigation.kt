package com.example.dovietha_bt.ui.main

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dovietha_bt.MusicServiceConnectionHelper
import com.example.dovietha_bt.MusicServiceConnectionHelper.musicService
import com.example.dovietha_bt.common.permission
import com.example.dovietha_bt.ui.Screen
import com.example.dovietha_bt.ui.main.home.HomeScreen
import com.example.dovietha_bt.ui.main.library.LibraryScreen
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistScreen
import com.example.dovietha_bt.ui.main.myplaylist.mymusic.MyMusicScreen
import com.example.dovietha_bt.ui.main.myplaylist.nowplaying.MiniPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun UnitedScreen(
    toPlaying: () -> Unit = {},
    goProfile: () -> Unit = {},
    viewModel: MainScreenViewModel = viewModel(),
) {
    val context = LocalContext.current
    val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
    var isPlayingBarClosed by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(100L)
        permission(context)
        isPlayingBarClosed = musicService?.getCurrentSong() == null
        Log.d("Is Playing Bar Closed", "progress: ${musicService?.getCurrentSong()}")
        viewModel.processIntent(MainScreenIntent.LoadUser)
    }
    LaunchedEffect(isPlayingBarClosed) {
        Log.d("AAAAAAAAA", "$isPlayingBarClosed")
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        backStack.clear()
                        backStack.add(Screen.Home)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                        Text("Home")
                    }
                }

                IconButton(
                    onClick = {
                        backStack.clear()
                        backStack.add(Screen.Library)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Playlist")
                        Text("Playlist")
                    }
                }
                IconButton(
                    onClick = {
                        backStack.clear()
                        backStack.add(Screen.MyPlaylist)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Menu, contentDescription = "My playlist")
                        Text("My playlist")
                    }
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            NavDisplay(
                modifier = Modifier,
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Screen.Home> {
                        Log.d("Check click", "click")
                        HomeScreen(goProfile = goProfile)
                    }
                    entry<Screen.Library> {
                        LibraryScreen(
                            onAddClicked = {
                                backStack.clear()
                                backStack.add(Screen.MyPlaylist)
                            },
                        )
                    }
                    entry<Screen.MyPlaylist> {
                        MyPlaylistScreen(
                            onClick = {
                                backStack.clear()
                                backStack.add(Screen.MusicList(it))
                            },
                        )
                    }
                    entry<Screen.MusicList> { (list) ->
                        MyMusicScreen(playlist = list, isCloseBar = { isPlayingBarClosed = it })
                    }
                },
            )
            //Log.d("Is Playing Bar Closed", "progress: ${isPlayingBarClosed}")
            if (!isPlayingBarClosed)
                MiniPlayerContainer(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = toPlaying,
                    closePlayingBar = { isPlayingBarClosed = it },
                )
        }
    }
}

@Composable
fun MiniPlayerContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    closePlayingBar: (Boolean) -> Unit = {},
) {
    val progress = remember { mutableStateOf(0f) }
    val isPlaying = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val service = musicService
            if (service != null && service.isPlaying()) {
                val duration = service.getDuration()
                val currentPos = service.getCurrentPosition()
                progress.value = if (duration > 0) currentPos / duration.toFloat() else 0f
                isPlaying.value = true
            } else {
                progress.value = 0f
                isPlaying.value = false
            }
            delay(500)
            
        }
    }
    MiniPlayer(
        modifier = modifier,
        onClick = onClick,
        isPlaying = isPlaying.value,
        progress = progress.value,
        closePlayingBar = closePlayingBar,
        onPlayPause = {
            if(isPlaying.value){
                Log.d("MiniPlayerContainer", "progress: ${isPlaying.value}")
                musicService?.pause()
            }
            else{
                musicService?.resume()
            }
        }
    )
}

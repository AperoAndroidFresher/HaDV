package com.example.dovietha_bt.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dovietha_bt.common.permission
import com.example.dovietha_bt.ui.Screen
import com.example.dovietha_bt.ui.main.home.HomeScreen
import com.example.dovietha_bt.ui.main.library.LibraryScreen
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistScreen
import com.example.dovietha_bt.ui.main.myplaylist.components.MyMusicScreen

@Preview(showBackground = true)
@Composable
fun UnitedScreen(goProfile: () -> Unit = {}) {
    val context = LocalContext.current
    val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
    LaunchedEffect(Unit) {
        permission(context)
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
                    modifier = Modifier.weight(1f)
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
                    modifier = Modifier.weight(1f)
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
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Menu, contentDescription = "My playlist")
                        Text("My playlist")
                    }
                }
            }
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier.padding(paddingValues),
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
                        }
                    )
                }
                entry<Screen.MyPlaylist> {
                    MyPlaylistScreen(onClick = {
                        backStack.clear()
                        backStack.add(Screen.MusicList(it))
                    })
                }
                entry<Screen.MusicList> { (list) ->
                    MyMusicScreen(playlist = list)
                }
            }
        )

    }

}
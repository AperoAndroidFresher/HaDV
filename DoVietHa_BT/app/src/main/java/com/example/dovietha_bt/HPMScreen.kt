package com.example.dovietha_bt

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dovietha_bt.my_playlist.MyPlaylistScreen

@Preview(showBackground = true)
@Composable
fun UnitedScreen(goProfile: () -> Unit = {}) {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
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
                        backStack.add(Screen.Playlist)
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
                entry<Screen.Playlist> {
                    Playlist()
                }
                entry<Screen.MyPlaylist> {
                    MyPlaylistScreen()
                }
            }
        )

    }

}
package com.example.dovietha_bt.ui.main.myplaylist

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.ui.main.myplaylist.model.MyPlaylistIntent
import com.example.dovietha_bt.ui.main.myplaylist.model.Option
import com.example.dovietha_bt.ui.main.myplaylist.model.PlaylistVM
import com.example.dovietha_bt.model.UserInformation

@Composable
fun MyPlaylistScreen(
    viewModel: MyPlaylistViewModel = viewModel(),
    onClick: (PlaylistVM) -> Unit = {},
    ) {
    val state = viewModel.state.collectAsState()
    var playlistName by remember { mutableStateOf("") }
    var addClicked by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        if (state.value.playlists.isEmpty()) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("You don't have any playlists. Click the \"+\" button to add")
                OutlinedButton(
                    onClick = { addClicked = true },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(painterResource(R.drawable.ic_add), "")
                }
            }
        } else {
            AllPlaylists(
                list = state.value.playlists,
                onOptionClick = { option, playlist ->
                    if (option.desc == "Remove Playlist") {
                        viewModel.processIntent(MyPlaylistIntent.RemovePlaylist(playlist.id))
                        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists)
                    }
                },
                option = listOf(Option(image = R.drawable.ic_remove, desc = "Remove Playlist")),
                onClick = onClick
            )
        }
        if (addClicked)
            AddDialog(
                name = playlistName,
                onDismissRequest = { addClicked = false },
                addPlaylist = {
                    viewModel.processIntent(
                        MyPlaylistIntent.AddPlaylist(playlistName, UserInformation.name?: "")
                    )
                    viewModel.processIntent(MyPlaylistIntent.LoadPlaylists)
                    Log.d("TAG", "AddDialog: ${state.value.playlists}")
                },
                onValueChange = { playlistName = it }
            )
    }
}

@Preview(showBackground = true)
@Composable
fun AddDialog(
    onDismissRequest: () -> Unit = {},
    addPlaylist: () -> Unit = {},
    name: String = "",
    onValueChange: (String) -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text("New Playlist")
        },
        text = {
            TextField(
                value = name,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        },
        dismissButton = {
            Text("Cancel", modifier = Modifier.clickable(onClick = onDismissRequest))
        },
        confirmButton = {
            Text("Create", modifier = Modifier.clickable(onClick = {
                addPlaylist()
                onDismissRequest()
            }))
        }
    )
}
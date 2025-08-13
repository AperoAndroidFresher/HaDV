package com.example.dovietha_bt.ui.main.myplaylist

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.common.UserInformation
import com.example.dovietha_bt.ui.main.myplaylist.components.AllPlaylists

@Composable
fun MyPlaylistScreen(
    viewModel: MyPlaylistViewModel = viewModel(),
    onClick: (PlaylistVM) -> Unit = {},
    isAddClicked:Boolean = false
) {
    val state = viewModel.state.collectAsState()
    var playlistName by remember { mutableStateOf("") }
    var playlistId by remember { mutableStateOf(0L) }
    var addClicked by remember { mutableStateOf(isAddClicked) }
    var renameClicked by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists(UserInformation.username))
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
                modifier = Modifier.align(Alignment.CenterEnd).clickable(onClick={addClicked = true})
            )
        }
        if (state.value.playlists.isEmpty()) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "You don't have any playlists. Click the \"+\" button to add",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
                OutlinedButton(
                    onClick = { addClicked = true },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .padding(24.dp)
                        .size(80.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        } else {
            AllPlaylists(
                list = state.value.playlists,
                onOptionClick = { option, playlist ->
                    if (option.desc == "Remove Playlist") {
                        viewModel.processIntent(MyPlaylistIntent.RemovePlaylist(playlist.id))
                        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists(UserInformation.username))
                    }
                    else {
                        renameClicked = true
                        playlistId = playlist.id
                    }
                },
                option = listOf(Option(image = R.drawable.ic_remove, desc = "Remove Playlist"), Option(image = R.drawable.ic_rename, desc = "Rename")),
                onClick = onClick
            )
        }
        if(renameClicked){
            AddDialog(
                name = playlistName,
                onDismissRequest = { renameClicked = false },
                addPlaylist = {
                    viewModel.processIntent(MyPlaylistIntent.RenamePlaylist(playlistId,playlistName))
                    viewModel.processIntent(MyPlaylistIntent.LoadPlaylists(UserInformation.username))
                    Log.d("TAG", "AddDialog: ${state.value.playlists}")
                },
                onValueChange = { playlistName = it }
            )
        }
        if (addClicked)
            AddDialog(
                name = playlistName,
                onDismissRequest = { addClicked = false },
                addPlaylist = {
                    Log.d("Check USER", "${UserInformation.name}")
                    viewModel.processIntent(
                        MyPlaylistIntent.AddPlaylist(playlistName=playlistName, username = UserInformation.username)
                    )
                    viewModel.processIntent(MyPlaylistIntent.LoadPlaylists(UserInformation.username))
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
                UserInformation.showData()
            }))
        }
    )
}

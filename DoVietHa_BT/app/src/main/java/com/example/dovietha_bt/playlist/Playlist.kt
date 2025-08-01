package com.example.dovietha_bt.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.myplaylist.model.Option
import com.example.dovietha_bt.myplaylist.view.ColumnList

val libOptions = listOf(
    Option(R.drawable.ic_remove, "Add to playlist"),
    Option(R.drawable.ic_share, "Share (Coming soon)")
)

@Composable
fun Playlist(viewModel: LibraryViewModel = viewModel(), onAddClicked: () -> Unit = {}) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadSong)
        event.collect { event ->
            when (event) {
                LibraryEvent.ShowDialog -> showDialog = true
            }
        }
    }
    Box() {
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
                Row() {
                    Button(
                        onClick = {}
                    ) {
                        Text("Local")
                    }

                    Spacer(Modifier.padding(8.dp))

                    Button(
                        onClick = {}
                    ) {
                        Text("Remote")
                    }
                }
                ColumnList(
                    list = state.value.musics,
                    option = libOptions,
                    onOptionClick = { option, music ->
                        if (option.desc == "Add to playlist") viewModel.processIntent(
                            LibraryIntent.AddToPlaylist(
                                music
                            )
                        )
                    }
                )
            }

        }
        if (showDialog) AddDialog({ showDialog = false }, Modifier.align(Alignment.Center), onAddClicked)
    }
}

@Composable
fun AddDialog(
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    onAddClicked:()->Unit = {}
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
    }
}
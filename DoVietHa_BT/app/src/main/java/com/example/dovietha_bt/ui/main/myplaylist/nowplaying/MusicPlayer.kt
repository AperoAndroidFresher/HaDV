package com.example.dovietha_bt.ui.main.myplaylist.nowplaying

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.MusicServiceConnectionHelper
import com.example.dovietha_bt.R
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel

@Preview(showBackground = true)
@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    isPlaying: Boolean = false,
    onPlayPause: () -> Unit = {},
    onClose: () -> Unit = {},
    onClick: () -> Unit = {},
    viewModel: MyPlaylistViewModel = viewModel(),
    closePlayingBar: (Boolean) -> Unit = {},
) {
    val state = viewModel.state.collectAsState()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF2C2C2C))
            .clickable(onClick = onClick),
    ) {
        Column {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                color = Color.Cyan,
                trackColor = Color.Gray,
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = onPlayPause) {
                    Icon(
                        painter = painterResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                        contentDescription = null,
                        tint = Color.White,
                        
                    )
                }

                Text(
                    text = state.value.currentSong.name,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

                Text(
                    text = state.value.currentSong.duration,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )

                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable(
                            onClick = {
                                closePlayingBar(true)
                                MusicServiceConnectionHelper.musicService?.kill()
                            },
                        ),
                    )
                }
            }
        }
    }
}


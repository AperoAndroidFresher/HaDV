package com.example.dovietha_bt.ui.main.myplaylist.nowplaying

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.MusicServiceConnectionHelper.musicService
import com.example.dovietha_bt.R
import com.example.dovietha_bt.database.converter.formatDuration
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistIntent
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NowPlayingScreen(
    viewModel: MyPlaylistViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    val service = musicService
    val state = viewModel.state.collectAsState()

    val currentTime = remember { mutableStateOf("00:00") }
    val totalTime = remember { mutableStateOf("00:00") }
    val progress = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val launchedService = musicService
            if (launchedService != null) {
                val currentPos = launchedService.getCurrentPosition()
                val duration = launchedService.getDuration()

                progress.value =if (duration > 0) {
                    currentPos.toFloat() / duration.toFloat()
                } else {
                    0f // Giá trị mặc định an toàn nếu duration không hợp lệ
                }
                currentTime.value = formatDuration(currentPos.toLong())
                totalTime.value = formatDuration(duration.toLong())
            }
            delay(500)
        }
    }

    LaunchedEffect(Unit) {
        service?.onSongChanged = {
            viewModel.processIntent(MyPlaylistIntent.CurrentSong(it))
        }
        service?.getCurrentSong()?.let {
            viewModel.processIntent(MyPlaylistIntent.CurrentSong(it))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        NowPlayingHeader(
            onBackClick = onBackClick, 
            onCloseClick = {
                onCloseClick()
            }
        )
        Body(state.value.currentSong.name, state.value.currentSong.author)
        MusicPlayerControls(
            onPlayClick = {
                viewModel.processIntent(MyPlaylistIntent.IsPlaying)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(100)
                    if (state.value.isPlaying) service?.pause()
                    else service?.resume()
                }
            },
            onNextClick = { service?.next() },
            onPrevClick = { service?.previous() },
            onRepeatClick = { viewModel.processIntent(MyPlaylistIntent.ToggleRepeat) },
            onShuffleClick = { viewModel.processIntent(MyPlaylistIntent.ToggleShuffle) },
            isShuffleOn = state.value.isShuffleOn,
            isRepeatOn = state.value.isRepeatOn,
            isPlaying = state.value.isPlaying,
            currentTime = currentTime.value,
            totalTime = totalTime.value,
            progress = progress.value,
        )
    }
}

@Composable
private fun NowPlayingHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.size(30.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.size(30.dp),
                )
            }
        }

        // Text ở giữa (trung tâm Box)
        Text(
            text = "Now Playing",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Body(
    title: String = "Title",
    artist: String = "Artist",
) {
    Column(Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "",
        )
        Spacer(Modifier.padding(8.dp))
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = Bold,
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            text = artist,
            fontWeight = Bold,
        )

    }
}

@Composable
fun MusicPlayerControls(
    currentTime: String = "02:07",
    totalTime: String = "02:43",
    progress: Float = 0.75f,
    isShuffleOn: Boolean = false,
    isRepeatOn: Boolean = false,
    isPlaying: Boolean = false,
    onPlayClick: () -> Unit = {},
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Time slider
        Slider(
            value = progress,
            onValueChange = { newPos ->
                val duration = musicService?.getDuration() ?: 0
                val newPos = (duration * newPos).toInt()
                musicService?.seekTo(newPos)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.Cyan,
                activeTrackColor = Color.Cyan,
            ),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = currentTime, color = MaterialTheme.colorScheme.onBackground)
            Text(text = totalTime, color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onShuffleClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = "Shuffle",
                    tint = if (isShuffleOn) Color.Cyan else MaterialTheme.colorScheme.onBackground,
                )
            }

            IconButton(onClick = onPrevClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_previous),
                    contentDescription = "Previous",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = Color.Cyan,
                        shape = CircleShape,
                    )
                    .clickable(onClick = onPlayClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = if (isPlaying) painterResource(R.drawable.ic_play) else painterResource(R.drawable.ic_pause),
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp),
                )
            }

            IconButton(onClick = onNextClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            IconButton(onClick = onRepeatClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_replay),
                    contentDescription = "Repeat",
                    tint = if (isRepeatOn) Color.Cyan else MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

package com.example.dovietha_bt.ui.main.myplaylist.nowplaying

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R

@Preview(showBackground = true)
@Composable
fun NowPlayingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        NowPlayingHeader()
        Body()
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
            fontWeight = Bold
        )
        Spacer(Modifier.padding(4.dp))
        Text(
            text = artist,
            fontWeight = Bold
        )
        MusicPlayerControls()
    }
    
}

@Composable
fun MusicPlayerControls(
    currentTime: String = "02:07",
    totalTime: String = "02:43",
    progress: Float = 0.75f,
    onPlayClick: () -> Unit = {},
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Seek bar
        Slider(
            value = progress,
            onValueChange = {},
            colors = SliderDefaults.colors(
                thumbColor = Color.Cyan,
                activeTrackColor = Color.Cyan,
                inactiveTrackColor = Color.Gray
            )
        )

        // Time labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = currentTime, fontSize = 12.sp)
            Text(text = totalTime, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Control buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onShuffleClick) {
                Icon(painter = painterResource(R.drawable.ic_shuffle), contentDescription = "Shuffle",tint = MaterialTheme.colorScheme.onBackground)
            }

            IconButton(onClick = onPrevClick) {
                Icon(painter = painterResource(R.drawable.ic_previous), contentDescription = "Previous",tint = MaterialTheme.colorScheme.onBackground)
            }

            // Play Button with gradient circle
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = Color.Cyan,
                        shape = CircleShape
                    )
                    .clickable(onClick = onPlayClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(onClick = onNextClick) {
                Icon(painter = painterResource(R.drawable.ic_next), contentDescription = "Next", tint = MaterialTheme.colorScheme.onBackground)
            }

            IconButton(onClick = onRepeatClick) {
                Icon(painter = painterResource(R.drawable.ic_replay), contentDescription = "Repeat", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

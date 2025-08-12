package com.example.dovietha_bt.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R

@Preview(showBackground = true)
@Composable
fun HomeScreen(goProfile: () -> Unit = {}) {
    MusicRankingScreen(onClick = goProfile)
}

@Composable
fun MusicRankingScreen(onClick: () -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        // Welcome row
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, CircleShape)
                        .clickable(onClick = onClick),
                ) // thay bằng Image nếu có avatar thật
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Welcome back !", color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
                    Text("chandrama", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Rankings title
        item {
            Text(
                text = "\uD83C\uDFC6 Rankings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Cyan,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Top Albums - Section header
        item {
            SectionTitle(title = "Top Albums")
        }

        // Top Albums - LazyVerticalGrid (2 rows, nhiều cột)
        item {
            val albums = listOf(
                "Palette", "Modern", "In Search of ...", "Modern", "Modern", "Modern",
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(albums) { album ->
                    Row(
                        modifier = Modifier
                            .width(180.dp)
                            .background(Color.DarkGray, RoundedCornerShape(8.dp)),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        Column(modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 12.dp)) {
                            Text(
                                text = album,
                                color = Color.White,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(Modifier.padding(3.dp))
                            Text(
                                text = "Taylor",
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }

        // Top Tracks - Section header
        item {
            SectionTitle(title = "Top Tracks")
        }

        // Top Tracks - LazyRow
        item {
            val tracks = listOf(
                Track("Espresso", "Sabrina Carpenter", 972864),
                Track("Chill Mix", "Sabrina Carpenter", 972864),
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                items(tracks) { track ->
                    TrackCard(track)
                }
            }
        }

        item {
            SectionTitle(title = "Top Artist")
        }

        // Top Artist - LazyRow
        item {
            val artists = listOf("QuanBui", "TranDucBo")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                items(artists) { artist ->
                    ArtistCard(artist)
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("See all", color = Color.Cyan, fontSize = 12.sp)
    }
}

data class Track(val title: String, val artist: String, val listener: Int)

@Composable
fun TrackCard(track: Track) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
    ) {
        // Background Image (nằm dưới cùng)
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Overlay nội dung text
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = track.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Column {
                Text(text = "${track.listener}", color = Color.White, fontSize = 12.sp)
                Text(text = track.artist, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ArtistCard(name: String) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
    ) {
        // Background Image (nằm dưới cùng)
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Overlay nội dung text
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}


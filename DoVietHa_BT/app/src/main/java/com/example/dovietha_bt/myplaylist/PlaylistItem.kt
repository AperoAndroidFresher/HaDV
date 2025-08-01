package com.example.dovietha_bt.myplaylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.dovietha_bt.R
import com.example.dovietha_bt.myplaylist.model.Option
import com.example.dovietha_bt.myplaylist.model.Playlist
import com.example.dovietha_bt.myplaylist.view.SimpleDropdownMenuOnly

@Composable
fun PlaylistItemColumn(
    image: ByteArray? = null,
    name: String = "Name",
    sumSongs: Int = 0,
    option: List<Option> = emptyList(),
    onOptionClick: (Option) -> Unit = {},
    viewModel: MyPlaylistViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(72.dp)

    ) {
        Image(
            musicImage(image),
            "",
            modifier = Modifier.Companion
                .size(72.dp)
                .clip(
                    RoundedCornerShape(5.dp)
                ),
            contentScale = ContentScale.Companion.Crop
        )
        Column(
            Modifier.Companion
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
            Spacer(Modifier.Companion.padding(1.dp))
            Text(
                text = "$sumSongs songs",
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 16.sp,
                color = Color.Companion.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
        }
        Row(modifier = Modifier.Companion.align(Alignment.Companion.CenterVertically)) {

            Spacer(Modifier.Companion.padding(8.dp))
            Icon(
                painterResource(R.drawable.ic_option),
                "",
                tint = Color.Companion.Black,
                modifier = Modifier.Companion
                    .size(20.dp)
                    .align(Alignment.Companion.CenterVertically)
                    .clickable(onClick = { menuExpanded = true })
            )
            SimpleDropdownMenuOnly(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                items = option,
                onItemClick = onOptionClick
            )
        }
    }
}

@Composable
fun musicImage( image: ByteArray?) : AsyncImagePainter{
    return rememberAsyncImagePainter(
        model = image ?: R.drawable.avatar,
        placeholder = painterResource(R.drawable.avatar),
        error = painterResource(R.drawable.avatar)
    )
}
package com.example.dovietha_bt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Music(
    val image: Int,
    val name: String,
    val author: String,
    val duration: String
)

data class Option(
    val image: Int,
    val desc: String
)

val a = listOf(
    Music(R.drawable.avatar, "Dung lam trai tim anh dau", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Buong doi tay nhau ra", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Con mua ngang qua", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Em cua ngay hom qua", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Nhu ngay hom qua", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Chung ta khong thuoc ve nhau", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Dung ve tre", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Nang am xa dan", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Khuon mat dang thuong", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Remember me", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Chung ta cua tuong lai", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Khong phai dang vua dau", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Lac troi", "Son Tung MTP", "04:23"),
    Music(R.drawable.avatar, "Noi nay co anh", "Son Tung MTP", "04:23"),
)

val options = listOf(
    Option(R.drawable.ic_remove, "Remove from playlist"),
    Option(R.drawable.ic_share,"Share (Coming soon)")
)
@Composable
fun MyPlaylistScreen() {
    var isViewChanged by remember { mutableStateOf(false) }
    val list = remember { mutableStateListOf<Music>().apply { addAll(a) } }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
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
            Row(
                Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_view),
                    "",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(onClick = { isViewChanged = !isViewChanged })
                )
                Spacer(Modifier.padding(8.dp))
                Icon(
                    painterResource(R.drawable.ic_sort_up),
                    "",
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        if (isViewChanged) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(list) { item ->
                    MusicItemGrid(
                        item.image,
                        item.name,
                        item.author,
                        item.duration,
                        onItemClick = { selected ->
                            if (selected.desc == "Remove from playlist")
                                list.remove(item)
                        }
                    )
                }
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(list) { item ->


                    MusicItemColumn(
                        image = item.image,
                        name = item.name,
                        author = item.author,
                        duration = item.duration,
                        onItemClick = { selected ->
                            if (selected.desc == "Remove from playlist")
                                list.remove(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MusicItemColumn(
    image: Int = R.drawable.ic_launcher_background,
    name: String = "Name",
    author: String = "author",
    duration: String = "00:00",
    onItemClick: (Option) -> Unit = {}
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)

    ) {
        Image(
            painterResource(image),
            "",
            modifier = Modifier
                .size(72.dp)
                .clip(
                    RoundedCornerShape(5.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(
                text = name,
                fontWeight = Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.padding(1.dp))
            Text(
                text = author,
                fontWeight = Bold,
                fontSize = 16.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                duration,
                fontSize = 20.sp,
            )
            Spacer(Modifier.padding(8.dp))
            Icon(
                painterResource(R.drawable.ic_option),
                "",
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = { menuExpanded = true })
            )
            SimpleDropdownMenuOnly(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                items = options,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun MusicItemGrid(
    image: Int = R.drawable.ic_launcher_background,
    name: String = "Name",
    author: String = "Author",
    duration: String = "00:00",
    onItemClick: (Option) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var menuExpanded by remember { mutableStateOf(false) }

        Box() {
            Image(
                painter = painterResource(image), "",
                modifier = Modifier
                    .size(135.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .padding(8.dp),

                ) {
                Icon(
                    painter = painterResource(R.drawable.ic_option),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable(onClick = { menuExpanded = true })
                )
                SimpleDropdownMenuOnly(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    items = options,
                    onItemClick = onItemClick
                )
            }
        }
        Spacer(Modifier.padding(4.dp))
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = author,
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.DarkGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.padding(2.dp))
        Text(
            text = duration,
            fontSize = 18.sp
        )
    }
}

@Composable
fun SimpleDropdownMenuOnly(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<Option>,
    onItemClick: (Option) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onItemClick(item)
                    onDismissRequest()
                },
                text = {
                    Text(text = item.desc)
                },
                leadingIcon = {Icon(painterResource(item.image),"")}
            )
        }
    }
}

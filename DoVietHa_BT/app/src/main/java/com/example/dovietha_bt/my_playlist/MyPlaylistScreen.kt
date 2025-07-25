package com.example.dovietha_bt.my_playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R
import com.example.dovietha_bt.my_playlist.model.Music
import com.example.dovietha_bt.my_playlist.model.Option

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
    Option(R.drawable.ic_share, "Share (Coming soon)")
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
            GridList(list)
        } else {
            ColumnList(list)
        }
    }
}


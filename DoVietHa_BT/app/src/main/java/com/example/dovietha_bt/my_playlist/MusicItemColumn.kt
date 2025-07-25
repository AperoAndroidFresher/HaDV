package com.example.dovietha_bt.my_playlist

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
import com.example.dovietha_bt.R
import com.example.dovietha_bt.my_playlist.model.Option

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
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(72.dp)

    ) {
        Image(
            painterResource(image),
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
                text = author,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 16.sp,
                color = Color.Companion.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
        }
        Row(modifier = Modifier.Companion.align(Alignment.Companion.CenterVertically)) {
            Text(
                duration,
                fontSize = 20.sp,
            )
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
                items = options,
                onItemClick = onItemClick
            )
        }
    }
}
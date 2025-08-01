package com.example.dovietha_bt.myplaylist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.myplaylist.MyPlaylistViewModel
import com.example.dovietha_bt.myplaylist.model.Option

@Preview(showBackground = true)
@Composable
fun MusicItemGrid(
    image: ByteArray? =null,
    name: String = "Name",
    author: String = "Author",
    duration: String = "00:00",
    option: List<Option> =emptyList(),
    onItemClick: (Option) -> Unit = {},
    viewModel: MyPlaylistViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val state = viewModel.state.collectAsState()
        var menuExpanded by remember { mutableStateOf(false) }
        Box() {
            Image(
                painter = musicImage(image), "",
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
                    items = option,
                    onItemClick = onItemClick
                )
            }
        }
        Spacer(Modifier.padding(4.dp))
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = author,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
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
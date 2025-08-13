package com.example.dovietha_bt.ui.main.myplaylist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.Option
import com.example.dovietha_bt.ui.main.myplaylist.MyPlaylistViewModel

@Composable
fun PlaylistItemColumn(
    image: ByteArray? = null,
    name: String = "Name",
    sumSongs: Int = 0,
    option: List<Option> = emptyList(),
    onOptionClick: (Option) -> Unit = {},
    viewModel: MyPlaylistViewModel = viewModel(),
    onClick: () -> Unit = {},
    haveOption: Boolean = true,
) {
    viewModel.state.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = onClick),

        ) {
        Image(
            musicImage(image),
            "",
            modifier = Modifier
                .size(72.dp)
                .clip(
                    RoundedCornerShape(5.dp),
                ),
            contentScale = ContentScale.Crop,
        )
        Column(
            Modifier.Companion
                .padding(12.dp)
                .weight(1f),
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )
            Spacer(Modifier.Companion.padding(1.dp))
            Text(
                text = "$sumSongs songs",
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 16.sp,
                color = Color.Companion.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
            )
        }
        if (haveOption) {
            Row(modifier = Modifier.Companion.align(Alignment.Companion.CenterVertically)) {

                Spacer(Modifier.Companion.padding(8.dp))
                Icon(
                    painterResource(R.drawable.ic_option),
                    "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.Companion
                        .size(20.dp)
                        .align(Alignment.Companion.CenterVertically)
                        .clickable(onClick = { menuExpanded = true }),
                )
                SimpleDropdownMenuOnly(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    items = option,
                    onItemClick = onOptionClick,
                )
            }
        }
    }
}

package com.example.dovietha_bt.myplaylist.view

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.dovietha_bt.myplaylist.model.Option

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
                leadingIcon = { Icon(painterResource(item.image), "") }
            )
        }
    }
}
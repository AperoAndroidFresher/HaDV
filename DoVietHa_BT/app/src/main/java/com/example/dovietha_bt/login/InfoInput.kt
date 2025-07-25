package com.example.dovietha_bt.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.R

@Preview(showBackground = true)
@Composable
fun InfoInput(
    leadingIcon: Int = R.drawable.ic_launcher_foreground,
    hint: String = "Hint",
    value:String="",
    onValueChange:(String) -> Unit = {},
    checkError:Boolean = false,
    errorDesc:String ="Error"
) {
    Column(Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.Companion
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painterResource(leadingIcon),
                    "",
                    modifier = Modifier.size(16.dp)
                )
            },
            placeholder = { Text(hint) }
        )
        if(checkError) {
            Text(errorDesc, color = Color.Red)
        }
    }
}
package com.example.dovietha_bt.information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputText(
    title: String = "Title",
    desc: String = "Desc...",
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit = {},
    isSingleLine: Boolean = true,
    editable: Boolean = false,
    isError: Boolean = false,
    ) {
    Column(modifier.fillMaxWidth()) {
        Text(title, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.padding(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    desc,
                    color = Color.Gray,
                    maxLines = 1,
                    fontSize = 14.sp
                )
            },
            modifier = textFieldModifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            singleLine = isSingleLine,
            keyboardOptions = keyboardOptions,
            enabled = editable,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary
            )
        )
        Spacer(Modifier.padding(4.dp))
        if (isError) Text("Invalid format", color = Color.Red)
        Spacer(Modifier.padding(7.dp))
    }
}
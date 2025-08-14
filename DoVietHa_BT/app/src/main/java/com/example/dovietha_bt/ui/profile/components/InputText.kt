package com.example.dovietha_bt.ui.profile.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    title: String = "Title",
    desc: String = "Desc...",
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit = {},
    isSingleLine: Boolean = true,
    editable: Boolean = false,
    isError: Boolean = false,
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

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
            shape = RoundedCornerShape(15.dp),
            singleLine = isSingleLine,
            keyboardOptions = keyboardOptions,
            enabled = editable,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = textFieldModifier
                .fillMaxWidth()
        )

        Spacer(Modifier.padding(4.dp))

        if (isError) {
            Text(
                text = stringResource(R.string.invalid_format),
                color = Color.Red,
            )
        }

        Spacer(Modifier.padding(7.dp))
    }
}

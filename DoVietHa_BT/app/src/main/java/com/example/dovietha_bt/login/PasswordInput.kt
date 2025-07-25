package com.example.dovietha_bt.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dovietha_bt.R

@Composable
fun PasswordInput(
    leadingIcon: Int = R.drawable.ic_password,
    hint: String = "Hint",
    isShowPassword: Boolean = true,
    onToggle: () -> Unit = {},
    value: String = "",
    onValueChange: (String) -> Unit = {},
    checkError: Boolean = false,
    errorDesc: String = "Error"
) {
    Column(Modifier.padding(8.dp)){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (!isShowPassword) PasswordVisualTransformation() else VisualTransformation.Companion.None,
            modifier = Modifier.Companion
                .fillMaxWidth(),
            leadingIcon = { Icon(painterResource(leadingIcon), "") },
            placeholder = { Text(hint) },
            trailingIcon = {
                Icon(
                    if (isShowPassword) painterResource(R.drawable.ic_visible) else painterResource(
                        R.drawable.ic_invisible
                    ),
                    "",
                    modifier = Modifier.Companion.clickable(onClick = onToggle)
                )
            }
        )
        if (checkError) {
            Text(errorDesc, color = Color.Red)
        }
    }
}
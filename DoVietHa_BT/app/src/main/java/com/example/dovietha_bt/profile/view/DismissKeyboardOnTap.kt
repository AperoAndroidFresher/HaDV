package com.example.dovietha_bt.profile.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun DismissKeyboardOnTap(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .clickable(
                indication = null, // không hiệu ứng ripple
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus() // bỏ focus khi tap ngoài
            }
    ) {
        content()
    }
}
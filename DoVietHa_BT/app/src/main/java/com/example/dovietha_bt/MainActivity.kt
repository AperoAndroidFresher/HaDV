package com.example.dovietha_bt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.dovietha_bt.common.UserPreferences
import com.example.dovietha_bt.ui.Navigator
import com.example.dovietha_bt.ui.profile.components.DismissKeyboardOnTap
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserPreferences.init(this)
        setContent {
            DoVietHa_BTTheme {
                DismissKeyboardOnTap{ Navigator() }
            }
        }
    }
}


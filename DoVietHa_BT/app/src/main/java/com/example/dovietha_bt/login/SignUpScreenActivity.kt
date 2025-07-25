package com.example.dovietha_bt.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class SignUpScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoVietHa_BTTheme {
                SignUpScreen(LocalContext.current)
            }
        }
    }
}

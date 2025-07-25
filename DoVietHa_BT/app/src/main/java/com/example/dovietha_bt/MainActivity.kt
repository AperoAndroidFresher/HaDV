package com.example.dovietha_bt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.dovietha_bt.information.DismissKeyboardOnTap
import com.example.dovietha_bt.information.InfoScreen
import com.example.dovietha_bt.login.LoginScreen
import com.example.dovietha_bt.login.SignUpScreen
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoVietHa_BTTheme {
                DismissKeyboardOnTap {
                    //MyPlaylistScreen()
                    //InfoScreen()
                    LoginScreen(LocalContext.current,intent)
                }
            }
        }
    }
}


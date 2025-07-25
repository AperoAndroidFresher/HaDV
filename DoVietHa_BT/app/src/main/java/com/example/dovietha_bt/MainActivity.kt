package com.example.dovietha_bt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dovietha_bt.my_playlist.MyPlaylistScreen
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoVietHa_BTTheme {
                DismissKeyboardOnTap {
                    //MyPlaylistScreen()
                    InfoScreen()
                }
            }
        }
    }
}


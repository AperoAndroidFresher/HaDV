package com.example.dovietha_bt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.dovietha_bt.profile.view.DismissKeyboardOnTap
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoVietHa_BTTheme {
                DismissKeyboardOnTap {
                    //MyPlaylistScreen()
                    //InfoScreen()
                    Navigator()
                    //LoginScreen(LocalContext.current,intent)
                }
            }
        }
    }
}


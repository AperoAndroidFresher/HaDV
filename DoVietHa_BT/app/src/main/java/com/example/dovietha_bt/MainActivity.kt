package com.example.dovietha_bt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.dovietha_bt.information.InfoScreenViewModel
import com.example.dovietha_bt.information.view.DismissKeyboardOnTap
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class MainActivity : ComponentActivity() {
    private val viewModel: InfoScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoVietHa_BTTheme {
                DismissKeyboardOnTap {
                    //MyPlaylistScreen()
                    //InfoScreen()
                    Example()
                    //LoginScreen(LocalContext.current,intent)
                }
            }
        }
    }
}



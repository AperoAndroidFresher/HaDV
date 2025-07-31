package com.example.dovietha_bt

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dovietha_bt.Navigator
import com.example.dovietha_bt.profile.InfoScreenViewModel
import com.example.dovietha_bt.profile.view.DismissKeyboardOnTap
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

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


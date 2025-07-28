package com.example.dovietha_bt

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.ui.NavDisplay
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
                    Example()
                    //LoginScreen(LocalContext.current,intent)
                }

            }
        }
    }
}



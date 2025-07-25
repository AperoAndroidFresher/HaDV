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
                    MyAppNavHost(intent)
                    //LoginScreen(LocalContext.current,intent)
                }
            }
        }
    }
}

@Composable
fun MyAppNavHost(intent : Intent = Intent()){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("main") { LoginScreen(LocalContext.current,intent) }
    }
}


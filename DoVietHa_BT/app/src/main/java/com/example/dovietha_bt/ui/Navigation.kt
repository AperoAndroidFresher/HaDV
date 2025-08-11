package com.example.dovietha_bt.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dovietha_bt.common.UserPreferences
import com.example.dovietha_bt.ui.login.LoginScreen
import com.example.dovietha_bt.ui.main.UnitedScreen
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM
import com.example.dovietha_bt.ui.main.myplaylist.nowplaying.NowPlayingScreen
import com.example.dovietha_bt.ui.profile.ProfileScreen
import com.example.dovietha_bt.ui.signup.SignUpScreen
import com.example.dovietha_bt.ui.splashscreen.SplashScreen

sealed interface Screen {
    data object SplashScreen : Screen
    data class Login(var username: String = "", var password: String = "") : Screen
    data object SignUp : Screen
    data object UnitedScreen : Screen
    data object Home : Screen
    data object Library : Screen
    data object MyPlaylist : Screen
    data object Profile : Screen
    data class MusicList(var playlist: PlaylistVM) : Screen
    data object NowPlaying : Screen
}

@Composable
fun Navigator() {
    val context = LocalContext.current
    val backStack = remember { mutableStateListOf<Screen>(Screen.SplashScreen) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.SplashScreen> {
                SplashScreen(
                    changeScreen = {
                        Log.d("TAG","${UserPreferences.getUsername()}")
                        if (UserPreferences.getUsername().isNullOrBlank()) {
                            backStack.clear()
                            backStack.add(Screen.Login())
                        } else {
                            backStack.clear()
                            backStack.add(Screen.UnitedScreen)
                        }
                    },
                )
            }
            entry<Screen.Login> { (username, password) ->
                LoginScreen(
                    username = username,
                    password = password,
                    onClick = { backStack.add(Screen.SignUp) },
                    onLogin = {
                        backStack.clear()
                        backStack.add(Screen.UnitedScreen)
                    },
                )
            }
            entry<Screen.SignUp> {
                SignUpScreen(
                    { username, password ->
                        backStack.add(Screen.Login(username, password))
                    },
                )
            }
            entry<Screen.UnitedScreen> {
                UnitedScreen(
                    goProfile = {
                        backStack.add(
                            Screen.Profile,
                        )
                    },
                    toPlaying = {
                        backStack.add(
                            Screen.NowPlaying
                        )
                    }
                )
            }
            entry<Screen.Profile> {
                ProfileScreen()
            }
            entry<Screen.NowPlaying>{
                NowPlayingScreen()
            }
        },
    )
}

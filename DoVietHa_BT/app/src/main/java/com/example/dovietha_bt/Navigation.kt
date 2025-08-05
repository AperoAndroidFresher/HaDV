package com.example.dovietha_bt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dovietha_bt.home_playlist_myplaylist.UnitedScreen
import com.example.dovietha_bt.profile.view.ProfileScreen
import com.example.dovietha_bt.login.view.LoginScreen
import com.example.dovietha_bt.signup.SignUpScreen
import com.example.dovietha_bt.splashscreen.SplashScreen
import com.example.dovietha_bt.myplaylist.model.PlaylistVM

sealed interface Screen {
    data object SplashScreen : Screen
    data class Login(var username: String = "", var password: String = "") : Screen
    data object SignUp : Screen
    data object UnitedScreen : Screen
    data object Home : Screen
    data object Library: Screen
    data object MyPlaylist : Screen
    data object Profile : Screen
    data class MusicList(var playlist: PlaylistVM) : Screen
}

@Composable
fun Navigator() {
    val backStack = remember { mutableStateListOf<Screen>(Screen.SplashScreen) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.SplashScreen> {
                SplashScreen {
                    backStack.clear()
                    backStack.add(Screen.Login())
                }
            }
            entry<Screen.Login> { (username, password) ->
                LoginScreen(
                    username = username,
                    password = password,
                    onClick = { backStack.add(Screen.SignUp) },
                    onLogin = {
                        backStack.clear()
                        backStack.add(Screen.UnitedScreen)
                    })
            }
            entry<Screen.SignUp> {
                SignUpScreen( { username, password ->
                    backStack.add(Screen.Login(username, password))
                })
            }
            entry<Screen.UnitedScreen> {
                UnitedScreen {
                    backStack.add(
                        Screen.Profile
                    )
                }
            }
            entry<Screen.Profile> {
                ProfileScreen()
            }
        }
    )
}
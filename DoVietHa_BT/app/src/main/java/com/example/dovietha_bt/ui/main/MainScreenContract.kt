package com.example.dovietha_bt.ui.main

sealed interface MainScreenIntent {
    data object LoadUser: MainScreenIntent
}

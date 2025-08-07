package com.example.dovietha_bt.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.UserPreferences
import com.example.dovietha_bt.common.UserInformation
import com.example.dovietha_bt.database.entity.User
import com.example.dovietha_bt.database.repository.impl.UserRepositoryImpl
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository = UserRepositoryImpl(application)
    fun processIntent(intent: MainScreenIntent) {
        when (intent) {
            MainScreenIntent.LoadUser -> {
                viewModelScope.launch{ 
                    val currentUser = userRepository.getUserByUsername(UserPreferences(application).getUsername() ?: "")
                    UserInformation.updateData(currentUser ?: User("","",""))    
                }
            }
        }
    }
}

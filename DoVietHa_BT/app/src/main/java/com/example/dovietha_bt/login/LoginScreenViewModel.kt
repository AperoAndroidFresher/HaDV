package com.example.dovietha_bt.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.repository.UserRepositoryImpl
import com.example.dovietha_bt.profile.UserInformation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(LoginState())
    var state = _state.asStateFlow()
    private val _event = MutableSharedFlow<LoginEvent>()
    var event = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EditPassword -> {
                _state.value = _state.value.copy(password = intent.password)
            }

            is LoginIntent.EditUsername -> {
                _state.value = _state.value.copy(username = intent.username)
            }

            LoginIntent.IsShowPassword -> {
                _state.value = _state.value.copy(isShowPassword = !_state.value.isShowPassword)
            }

            LoginIntent.IsValid -> {
                viewModelScope.launch {
                    val check =
                        UserRepositoryImpl(application).getUserByUsername(_state.value.username)
                    if (check == null || check.password != _state.value.password) {
                        _event.emit(LoginEvent.ShowNotify)
                    } else {
                        UserInformation.username = _state.value.username
                        UserInformation.password = _state.value.password
                        _state.value = _state.value.copy(isValid = true)
                    }
                }
            }

            is LoginIntent.InitData -> {
                _state.value = _state.value.copy(
                    username = intent.username,
                    password = intent.password
                )
            }
        }
    }
}

package com.example.dovietha_bt.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.model.userList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState())
    var state = _state.asStateFlow()
    private val _event = MutableSharedFlow<LoginEvent>()
    var event = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.EditPassword ->{
                _state.value = _state.value.copy(password = intent.password)
            }
            is LoginIntent.EditUsername -> {
                _state.value = _state.value.copy(username = intent.username)
            }
            LoginIntent.IsShowPassword -> {
                _state.value = _state.value.copy(isShowPassword = !_state.value.isShowPassword)
            }
            LoginIntent.IsValid -> {
                userList.forEach {
                    if(it.username != _state.value.username || it.password != _state.value.password){
                        viewModelScope.launch {
                            _event.emit(LoginEvent.ShowNotify)
                        }
                    }
                    else _state.value = _state.value.copy(isValid = true)
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
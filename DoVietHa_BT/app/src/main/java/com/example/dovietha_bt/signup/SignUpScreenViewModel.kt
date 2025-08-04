package com.example.dovietha_bt.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.db.AppDB
import com.example.dovietha_bt.db.entity.User
import com.example.dovietha_bt.db.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(SignUpState())
    var state = _state.asStateFlow()
    private val _event = MutableSharedFlow<SignUpEvent>()
    var event = _event.asSharedFlow()

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.EditPassword -> {
                _state.value = _state.value.copy(password = intent.password)
            }

            is SignUpIntent.EditUsername -> {
                _state.value = _state.value.copy(username = intent.username)
            }

            SignUpIntent.IsShowPassword -> {
                _state.value = _state.value.copy(isShowPassword = !_state.value.isShowPassword)
            }

            SignUpIntent.IsValid -> {
                viewModelScope.launch {
                    val userRepository = UserRepositoryImpl(application)
                    var isValid = true
                    val errorUsername =
                        Regex("[^a-z0-9]").containsMatchIn(_state.value.username.lowercase()) || _state.value.username.isEmpty()
                    val errorPassword =
                        Regex("[^a-zA-Z0-9]").containsMatchIn(_state.value.password) || _state.value.password.isEmpty()
                    val errorConfirmPass =
                        Regex("[^a-zA-Z0-9]").containsMatchIn(_state.value.confirmPassword) || _state.value.confirmPassword != _state.value.password
                    val errorEmail =
                        !Regex("[a-z0-9._-]+@apero\\.com$").matches(_state.value.email) || _state.value.email.isEmpty()
                    if (errorUsername) {
                        _state.value = _state.value.copy(username = "")
                        isValid = false
                    }
                    if (errorPassword) {
                        _state.value = _state.value.copy(password = "")
                        isValid = false
                    }
                    if (errorConfirmPass) {
                        _state.value = _state.value.copy(confirmPassword = "")
                        isValid = false
                    }
                    if (errorEmail) {
                        _state.value = _state.value.copy(email = "")
                        isValid = false
                    }
                    val existingUser = userRepository.getUserByUsername(_state.value.username)
                    if (existingUser == null) {
                        userRepository.addUser(
                            User(
                                userName = _state.value.username,
                                password = _state.value.password,
                                email = _state.value.email
                            )
                        )
                        Log.d("isValid", "True")
                    } else {
                        isValid = false
                        Log.d("isValid", "False")
                    }
                    _state.value = _state.value.copy(
                        errUsername = errorUsername,
                        errPassword = errorPassword,
                        errConfirmPass = errorConfirmPass,
                        errEmail = errorEmail,
                        isValid = isValid
                    )
                    if (!isValid) {
                        _event.emit(SignUpEvent.ShowNotify) // Hoặc event khác
                    }
                }

            }

            is SignUpIntent.EditConfirmPass -> {
                _state.value = _state.value.copy(confirmPassword = intent.confirmPassword)
            }

            is SignUpIntent.EditEmail -> {
                _state.value = _state.value.copy(email = intent.email)
            }

            SignUpIntent.IsShowConfirmPassword -> {
                _state.value =
                    _state.value.copy(isShowConfirmPass = !_state.value.isShowConfirmPass)
            }
        }
    }
}
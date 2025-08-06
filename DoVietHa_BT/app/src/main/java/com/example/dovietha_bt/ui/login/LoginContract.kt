package com.example.dovietha_bt.ui.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val email:String = "",
    val isShowPassword: Boolean = false,
    val isValid: Boolean = false
)

sealed interface LoginIntent{
    data class EditUsername(val username:String): LoginIntent
    data class EditPassword(val password:String): LoginIntent
    data object IsShowPassword: LoginIntent
    data object IsValid : LoginIntent
    data class InitData(val username: String,val password: String): LoginIntent
}

sealed interface LoginEvent{
    object ShowNotify: LoginEvent
   // data class InitDataEvent(val username: String,val password: String): LoginEvent
}

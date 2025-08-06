package com.example.dovietha_bt.ui.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email:String = "",
    val isShowPassword: Boolean = false,
    val isShowConfirmPass : Boolean=false,
    val errUsername: Boolean = false,
    val errPassword: Boolean = false,
    val errConfirmPass: Boolean = false,
    val errEmail: Boolean = false,
    val isValid: Boolean = false,
)

sealed interface SignUpIntent{
    data class EditUsername(val username:String):SignUpIntent
    data class EditPassword(val password:String): SignUpIntent
    data class EditConfirmPass(val confirmPassword:String): SignUpIntent
    data class EditEmail(val email:String): SignUpIntent
    data object IsShowPassword: SignUpIntent
    data object IsShowConfirmPassword: SignUpIntent
    data object IsValid : SignUpIntent
}

sealed interface SignUpEvent{
    object ShowNotify: SignUpEvent
   // data class InitDataEvent(val username: String,val password: String): LoginEvent
}

package com.example.dovietha_bt.profile

import android.net.Uri
import com.example.dovietha_bt.ui.theme.Theme
import com.example.dovietha_bt.ui.theme.lightTheme

data class InfoState(
    val name: String = "",
    val phone: String = "",
    val uni: String = "",
    val desc: String = "",
    val avatarUri: Uri? = null,
    val isEditing: Boolean = false,
    val isDarkMode: Boolean = false,
    val showDialog: Boolean = false,
    val nameError: Boolean = false,
    val phoneError: Boolean = false,
    val uniError: Boolean = false,
    val currentTheme: Theme = lightTheme
)

sealed interface InfoIntent{
    data object LoadInfo:InfoIntent
    object Editable: InfoIntent
    data class UpdateName(val name: String): InfoIntent
    data class UpdatePhone(val phone: String): InfoIntent
    data class UpdateUni(val uni: String): InfoIntent
    data class UpdateDesc(val desc: String): InfoIntent
    data class UpdateAvatar(val avatarUri: Uri?) : InfoIntent
    object ToggleTheme: InfoIntent
    object SubmitInfo: InfoIntent
    object TriggerImagePicker : InfoIntent
}

sealed interface InfoEvent{
    object ShowDialog: InfoEvent
    object ShowImagePicker : InfoEvent
}
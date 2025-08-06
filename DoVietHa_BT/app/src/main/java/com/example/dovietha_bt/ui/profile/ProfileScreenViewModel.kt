package com.example.dovietha_bt.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.common.UserInformation
import com.example.dovietha_bt.database.repository.impl.UserRepositoryImpl
import com.example.dovietha_bt.ui.theme.darkTheme
import com.example.dovietha_bt.ui.theme.lightTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(InfoState())
    var state = _state.asStateFlow()
    private val _event = MutableSharedFlow<InfoEvent>()
    var event = _event.asSharedFlow()
    val userRepository = UserRepositoryImpl(application)

    fun processIntent(intent: InfoIntent) {
        when (intent) {
            is InfoIntent.Editable -> {
                _state.value = _state.value.copy(isEditing = true)
            }

            is InfoIntent.LoadInfo -> {
                _state.value = _state.value.copy(
                    name = UserInformation.name ?: "",
                    phone = UserInformation.phone ?: "",
                    uni = UserInformation.university ?: "",
                    desc = UserInformation.desc ?: "",
                    avatarUri = UserInformation.image
                )
            }

            is InfoIntent.SubmitInfo -> {
                viewModelScope.launch {
                    submitUserInfo()
                }
            }

            is InfoIntent.ToggleTheme -> {
                val isDarkTheme = !_state.value.isDarkMode
                _state.value = _state.value.copy(
                    isDarkMode = isDarkTheme,
                    currentTheme = if (isDarkTheme) darkTheme else lightTheme
                )
            }

            is InfoIntent.UpdateAvatar -> {
                _state.value = _state.value.copy(avatarUri = intent.avatarUri)
            }

            is InfoIntent.UpdateDesc -> {
                _state.value = _state.value.copy(desc = intent.desc)
            }

            is InfoIntent.UpdateName -> {
                _state.value = _state.value.copy(name = intent.name)
            }

            is InfoIntent.UpdatePhone -> {
                _state.value = _state.value.copy(phone = intent.phone)
            }

            is InfoIntent.UpdateUni -> {
                _state.value = _state.value.copy(uni = intent.uni)
            }

            is InfoIntent.TriggerImagePicker -> {
                viewModelScope.launch {
                    _event.emit(InfoEvent.ShowImagePicker)
                }
            }
        }
    }

    private suspend fun submitUserInfo() {
        val nameErr =
            _state.value.name.isBlank() || Regex("[^a-zA-Z ]").containsMatchIn(_state.value.name)
        val phoneErr =
            _state.value.phone.isBlank() || Regex("[^0-9]").containsMatchIn(_state.value.phone)
        val universityErr =
            _state.value.uni.isBlank() || Regex("[^a-zA-Z ]").containsMatchIn(_state.value.uni)

        _state.value = _state.value.copy(
            nameError = nameErr,
            phoneError = phoneErr,
            uniError = universityErr
        )

        if (!nameErr && !phoneErr && !universityErr) {
            val recentUser = userRepository.getUserByUsername(UserInformation.username)

            UserInformation.name = _state.value.name
            UserInformation.phone = _state.value.phone
            UserInformation.university = _state.value.uni
            UserInformation.desc = _state.value.desc
            UserInformation.image = _state.value.avatarUri

            val updateUser = recentUser?.copy(
                profileName = UserInformation.name,
                phoneNumber = UserInformation.phone,
                university = UserInformation.university,
                desc = UserInformation.desc,
                avatarUrl = UserInformation.image.toString()
            )

            if (updateUser != null) {
                userRepository.updateUser(updateUser)
            }

            _state.value = _state.value.copy(isEditing = false)

            _event.emit(InfoEvent.ShowDialog)
        }
    }

}
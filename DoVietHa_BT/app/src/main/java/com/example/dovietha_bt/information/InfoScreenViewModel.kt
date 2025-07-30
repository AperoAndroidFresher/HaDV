package com.example.dovietha_bt.information

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovietha_bt.ui.theme.darkTheme
import com.example.dovietha_bt.ui.theme.lightTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow<InfoState>(InfoState())
    var state = _state.asStateFlow()
    private val _event = MutableSharedFlow<InfoEvent>()
    var event = _event.asSharedFlow()

    fun processIntent(intent: InfoIntent){
        when(intent){
            is InfoIntent.Editable -> {
                _state.value = _state.value.copy(isEditing = true)
            }
            is InfoIntent.LoadInfo -> {
                _state.value = InfoState(
                    name = UserInformation.name,
                    phone = UserInformation.phone,
                    uni = UserInformation.university,
                    desc = UserInformation.desc,
                    avatarUri = UserInformation.image
                )
            }
            is InfoIntent.SubmitInfo -> {
                val nameErr = _state.value.name.isBlank() || Regex("[^a-zA-Z ]").containsMatchIn(_state.value.name)
                val phoneErr = _state.value.phone.isBlank() || Regex("[^0-9]").containsMatchIn(_state.value.phone)
                val universityErr = _state.value.uni.isBlank() || Regex("[^a-zA-Z ]").containsMatchIn(_state.value.uni)

                _state.value = _state.value.copy(
                    nameError = nameErr,
                    phoneError = phoneErr,
                    uniError = universityErr
                )

                if (!nameErr && !phoneErr && !universityErr) {
                    UserInformation.name = _state.value.name
                    UserInformation.phone = _state.value.phone
                    UserInformation.university = _state.value.uni
                    UserInformation.desc = _state.value.desc
                    UserInformation.image = _state.value.avatarUri

                    _state.value = _state.value.copy(isEditing = false)

                    viewModelScope.launch {
                        _event.emit(InfoEvent.ShowDialog)
                    }
                }
            }
            is InfoIntent.ToggleTheme -> {
                val isDarkTheme = !_state.value.isDarkMode
                _state.value = _state.value.copy(
                    isDarkMode = isDarkTheme,
                    currentTheme = if(isDarkTheme) darkTheme else lightTheme
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
}
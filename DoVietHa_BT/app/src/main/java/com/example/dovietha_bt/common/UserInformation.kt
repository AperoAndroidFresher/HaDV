package com.example.dovietha_bt.common

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.dovietha_bt.UserPreferences
import com.example.dovietha_bt.database.entity.User

object UserInformation {
    var username: String = ""
    var name: String? = null
    var phone: String? = null
    var university: String? = null
    var desc: String? = null
    var image: Uri? = null
    fun updateData(user: User){
        username = user.userName
        phone = user.phoneNumber
        university = user.university
        desc = user.desc
        image = user.avatarUrl.takeIf { it?.isNotBlank() == true }?.toUri()
    }
}

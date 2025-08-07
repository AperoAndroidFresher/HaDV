package com.example.dovietha_bt.common

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.dovietha_bt.database.entity.User

object UserInformation {
    var username: String = ""
    var name: String? = null
    var phone: String? = null
    var university: String? = null
    var desc: String? = null
    var image: Uri? = null
    
    fun updateData(user: User) {
        username = user.userName
        name = user.profileName
        phone = user.phoneNumber
        university = user.university
        desc = user.desc
        image = user.avatarUrl.takeIf { it?.isNotBlank() == true }?.toUri()
    }

    fun showData() {
        Log.d("UserInformation", "Username: $username, Name: $name, Phone: $phone, University: $university, Desc: $desc, Image: $image")
    }
}

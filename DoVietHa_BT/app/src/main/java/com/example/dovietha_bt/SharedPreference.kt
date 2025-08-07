package com.example.dovietha_bt

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit

@SuppressLint("CommitPrefEdits")
class UserPreferences(context: Context) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    
    fun saveUser(username:String){
        sharedPref.edit().putString("username",username)
    }
    fun getUsername(): String? = sharedPref.getString("username", null)
    fun logout() {
        sharedPref.edit { clear() }
    }
}

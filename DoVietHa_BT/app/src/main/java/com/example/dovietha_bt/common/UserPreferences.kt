package com.example.dovietha_bt.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


object UserPreferences {
    var sharedPref : SharedPreferences? = null
    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    @SuppressLint("CommitPrefEdits")
    fun saveUser(username:String){
        sharedPref?.edit()?.putString("username",username)?.apply()
    }
    fun getUsername(): String? = sharedPref?.getString("username", null)
    fun logout() { 
        sharedPref?.edit { clear() }
    }
}

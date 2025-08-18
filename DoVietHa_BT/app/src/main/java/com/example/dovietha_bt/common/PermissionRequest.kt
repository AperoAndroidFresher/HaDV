package com.example.dovietha_bt.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun permission(context: Context) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO)
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
            1
        )
    }
}
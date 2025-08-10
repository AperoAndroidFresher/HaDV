package com.example.dovietha_bt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundWithNotification()
        if (intent?.action == "PLAY") {
            val uriString = intent.getStringExtra("MUSIC_URI")
            uriString?.let {
                val uri = it.toUri()
                playMusic(uri)
            }
        }
        return START_STICKY
    }

    private fun playMusic(uri: Uri) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@MusicService, uri)
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            isLooping = true
            prepare()
            start()
        }
//        startForegroundWithNotification()
    }

    private fun startForegroundWithNotification() {
        val channelId = "MUSIC_CHANNEL"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Nhạc nền", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Đang phát nhạc")
            .setContentText("Nhạc đang chạy nền")
            .setSmallIcon(R.drawable.ic_musicnote) // Đặt icon trong res/drawable
            .build()

        startForeground(1, notification)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

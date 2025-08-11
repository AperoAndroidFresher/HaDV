package com.example.dovietha_bt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM

class MusicPlayerService : Service() {
    val binder = MusicBinder()
    private lateinit var mediaSession: MediaSessionCompat
    private var mediaPlayer: MediaPlayer? = null
    var songList = listOf<MusicVM>()
    private var currentIndex = 0
    private var isRepeat = false
    private var isShuffle = false

    inner class MusicBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSession = MediaSessionCompat(this, "MusicService")
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession.isActive = true
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MusicPlayerService", "onStartCommand called")
        
        when (intent?.action) {
            ACTION_PLAY -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableArrayListExtra("MUSIC_LIST", MusicVM::class.java)?.let {
                        songList = it
                    }
                }

                else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableArrayListExtra<MusicVM>("MUSIC_LIST")?.let {
                        songList = it
                    }
                }
                
                currentIndex = intent.getIntExtra("CURRENT_INDEX", 0)
                Log.d("Check Song Index","$currentIndex")
                playCurrent()
            }
            ACTION_RESUME -> resume()
            
            ACTION_PAUSE -> pause()
            
            ACTION_NEXT -> next()
            
            ACTION_PREVIOUS -> previous()
        }

        return START_STICKY
    }

    fun playCurrent() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, songList[currentIndex].path.toUri())
        mediaPlayer?.setOnCompletionListener {
            if (isRepeat) {
                playCurrent()
            } else {
                next()
            }
        }
        mediaPlayer?.start()
        startForegroundNotification()
    }
    fun resume() {
        mediaPlayer?.start()
    }
    fun pause() {
        mediaPlayer?.pause()
    }

    fun next() {
        mediaPlayer?.release()
        currentIndex = if (isShuffle) {
            (songList.indices).random()
        } else {
            (currentIndex + 1) % songList.size
        }
        playCurrent()
    }

    fun previous() {
        mediaPlayer?.release()
        currentIndex = if (isShuffle) {
            (songList.indices).random()
        } else {
            if (currentIndex - 1 < 0) songList.size - 1 else currentIndex - 1
        }
        playCurrent()
    }

    fun kill() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }
        mediaPlayer = null
        stopForeground(true)
        stopSelf()
    }

    fun toggleRepeat() {
        isRepeat = !isRepeat
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }
    
    fun isPlaying(): Boolean{
        return mediaPlayer?.isPlaying ?: false
    }
    
    fun getCurrentIndex():Int{
        return currentIndex
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_channel_id",
                "Music Playback",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun startForegroundNotification() {
        val playIntent = Intent(this, MusicPlayerService::class.java).apply { action = ACTION_RESUME }
        val pauseIntent = Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PAUSE }
        val nextIntent = Intent(this, MusicPlayerService::class.java).apply { action = ACTION_NEXT }
        val prevIntent = Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PREVIOUS }

        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent = PendingIntent.getService(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)
        val prevPendingIntent = PendingIntent.getService(this, 0, prevIntent, PendingIntent.FLAG_IMMUTABLE)

        // Mô phỏng thông tin bài hát
        val songTitle = "Tên bài hát"
        val songArtist = "Tên nghệ sĩ"

        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadata.METADATA_KEY_TITLE, songTitle)
            .putString(MediaMetadata.METADATA_KEY_ARTIST, songArtist)
            .build()

        mediaSession.setMetadata(metadata)

        val style = androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(1, 2, 3) // Chọn thứ tự hiển thị nút

        val notification = NotificationCompat.Builder(this, "music_channel_id")
            .setContentTitle(songTitle)
            .setContentText(songArtist)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_launcher_foreground, "Previous", prevPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Play", playPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Pause", pausePendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Next", nextPendingIntent)
            .setStyle(style)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
        startForeground(1, notification)
    }

    companion object {
        const val ACTION_PLAY = "com.example.myservice.ACTION_PLAY"
        const val ACTION_RESUME = "com.example.myservice.ACTION_RESUME"
        const val ACTION_PAUSE = "com.example.myservice.ACTION_PAUSE"
        const val ACTION_NEXT = "com.example.myservice.ACTION_NEXT"
        const val ACTION_PREVIOUS = "com.example.myservice.ACTION_PREVIOUS"
    }
}


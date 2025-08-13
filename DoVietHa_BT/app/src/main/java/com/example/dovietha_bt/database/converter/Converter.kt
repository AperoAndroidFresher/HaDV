package com.example.dovietha_bt.database.converter

import android.annotation.SuppressLint
import com.example.dovietha_bt.common.getEmbeddedImageBytes
import com.example.dovietha_bt.database.entity.Music
import com.example.dovietha_bt.database.entity.Playlist
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM
import com.example.dovietha_bt.ui.main.myplaylist.PlaylistVM
import java.util.Locale

fun Playlist.toPlaylistVM(listMusic: List<MusicVM>): PlaylistVM {
    return PlaylistVM(
        id = this.playlistId,
        name = this.name,
        username = this.username,
        musics = listMusic
    )
}

fun Music.toMusicVM(): MusicVM {
    return MusicVM(
        id = this.musicId,
        name = this.name,
        author = this.author,
        duration = this.duration,
        path = this.image ?: ""
    )
}

fun MusicVM.toMusic(): Music {
    return Music(
        musicId = this.id,
        name = this.name,
        author = this.author,
        duration = this.duration,
        image = this.path
    )
}

fun formatDuration(durationInMillis: Long): String {
    val totalSeconds = durationInMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, seconds)
}

fun parseDurationToMillis(timeString: String): Long {
    val parts = timeString.split(":")
    if (parts.size != 2) return 0L

    val minutes = parts[0].toLongOrNull() ?: return 0L
    val seconds = parts[1].toLongOrNull() ?: return 0L

    return (minutes * 60 + seconds) * 1000
}

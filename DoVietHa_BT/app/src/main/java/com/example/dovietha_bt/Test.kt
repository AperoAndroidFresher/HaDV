package com.example.dovietha_bt

import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.dovietha_bt.myplaylist.model.Music

fun getAllMp3Files(context: Context): List<Music> {
    val listMusic = mutableListOf<Music>()
    val contentResolver: ContentResolver = context.contentResolver
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val selection = "${MediaStore.Audio.Media.DURATION}>0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )
    val cursor = contentResolver.query(uri, projection, selection, null, null)
    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val title = it.getString(titleColumn)
            val artist = it.getString(artistColumn)
            val duration = it.getString(durationColumn)
            val data = it.getString(dataColumn)
            val image = getEmbeddedImageBytes(data)
            val music = Music(image = image, name = title, author = artist, duration = formatDuration(duration.toLong()))
            Log.d("check", "ID: $id, title: $title, artist: $artist, data: $data, image:$image")
            listMusic.add(music)
        }
    }
    return listMusic
}

fun getEmbeddedImageBytes(songPath: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(songPath)
        retriever.embeddedPicture
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

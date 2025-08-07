package com.example.dovietha_bt.common

import com.example.dovietha_bt.api.music.ApiMusic
import com.example.dovietha_bt.database.converter.formatDuration
import com.example.dovietha_bt.ui.main.myplaylist.MusicVM

fun ApiMusic.toMusicVM(localFile: String): MusicVM = MusicVM(
    name = title,
    author = artist,
    duration = formatDuration(duration.toLong()),
    path = localFile,
    image = getEmbeddedImageBytes(localFile)
)

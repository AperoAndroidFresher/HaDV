package com.example.dovietha_bt.db.converter

import com.example.dovietha_bt.db.entity.Music
import com.example.dovietha_bt.db.entity.Playlist
import com.example.dovietha_bt.getEmbeddedImageBytes
import com.example.dovietha_bt.myplaylist.model.MusicVM
import com.example.dovietha_bt.myplaylist.model.PlaylistVM

fun Playlist.toPlaylistVM(listMusic: List<MusicVM>): PlaylistVM{
    return PlaylistVM(
        id = this.playlistId,
        name = this.name,
        username = this.username,
        musics = listMusic
    )
}
fun Music.toMusicVM(): MusicVM{
    return MusicVM(
        id = this.musicId,
        name = this.name,
        author = this.author,
        duration = this.duration,
        image = getEmbeddedImageBytes(this.image ?:""),
        pathImg = this.image?:""
    )
}
fun MusicVM.toMusic(): Music{
    return Music(
        musicId = this.id,
        name = this.name,
        author = this.author,
        duration = this.duration,
        image = this.pathImg
    )
}
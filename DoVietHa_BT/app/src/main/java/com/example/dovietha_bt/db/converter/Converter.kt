package com.example.dovietha_bt.db.converter

import com.example.dovietha_bt.db.entity.Playlist
import com.example.dovietha_bt.myplaylist.model.PlaylistVM

fun Playlist.toPlaylistVM(): PlaylistVM{
    return PlaylistVM(
        id = this.playlistId,
        name = this.name,
        username = this.username
    )
}
package com.example.dovietha_bt.myplaylist.model

data class MusicVM(
    val id:Long = 0,
    val image: ByteArray? = null,
    val name: String = "",
    val author: String = "",
    val duration: String ="",
    val pathImg: String = ""
)

data class PlaylistVM(
    val id: Long = 0,
    val name:String = "",
    val username:String = "",
    val musics: List<MusicVM> = emptyList(),
)



data class MyPlaylistState(
    val playlists: List<PlaylistVM> = emptyList(),
    val musics: List<MusicVM> = emptyList(),
    val isViewChange: Boolean = false,
    val showOption: Boolean = false,
    val playlistName:String =""
)

sealed interface MyPlaylistIntent{
    data class AddPlaylist(val playlistName: String, val username: String): MyPlaylistIntent
    data class RemovePlaylist(val playlistId: Long) : MyPlaylistIntent
    data class RenamePlaylist(val playlistId: Long, val newPlaylistName: String): MyPlaylistIntent
    object ToggleView : MyPlaylistIntent
    data class RemoveSong(val musicId: Long, val playlistId:Long) : MyPlaylistIntent
    data object ShowOption : MyPlaylistIntent
    data object HideOption : MyPlaylistIntent
}
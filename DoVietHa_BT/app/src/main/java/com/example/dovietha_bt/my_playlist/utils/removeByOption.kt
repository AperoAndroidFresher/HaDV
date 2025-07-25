package com.example.dovietha_bt.my_playlist.utils

import com.example.dovietha_bt.my_playlist.model.Music
import com.example.dovietha_bt.my_playlist.model.Option

fun MutableList<Music>.removeByOption(option: Option, selected: Music) {
    if (option.desc == "Remove from playlist") this.remove(selected)
}
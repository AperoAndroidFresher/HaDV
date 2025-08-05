package com.example.dovietha_bt.db.repository.impl

import android.content.Context
import com.example.dovietha_bt.db.AppDB
import com.example.dovietha_bt.db.entity.Music
import com.example.dovietha_bt.db.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class MusicRepositoryImpl(context: Context): MusicRepository {
    val musicDao = AppDB.getInstance(context).MusicDao()
    override suspend fun getAllMusics(): Flow<List<Music>> {
        return musicDao.getAllMusics()
    }

    override suspend fun getMusicsById(id: Long): Music {
        return musicDao.getMusicsById(id)
    }

    override suspend fun insertMusic(music: Music):Long {
        return musicDao.insertMusic(music)
    }
}
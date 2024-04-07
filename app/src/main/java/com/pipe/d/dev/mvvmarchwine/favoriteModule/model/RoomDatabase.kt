package com.pipe.d.dev.mvvmarchwine.favoriteModule.model


import com.pipe.d.dev.mvvmarchwine.WineApplication
import com.pipe.d.dev.mvvmarchwine.common.dataAccess.room.WineDao
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine

class RoomDatabase {
    private val dao: WineDao by lazy { WineApplication.database.wineDao() }

    fun getAllWines() = dao.getAllWines()
    fun addWine(wine: Wine) = dao.addWine(wine)
    fun deleteWine(wine: Wine) = dao.deleteWine(wine)
}
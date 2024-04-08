package com.pipe.d.dev.mvvmarchwine.updateModule.model

import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.MyException
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants

class UpdateRepository(private val db: RoomDatabase) {
    fun requestWine(id: Double, callback: (Wine) -> Unit) {
        try {
            val result = db.getWineById(id)
            callback(result)
        } catch (e: Exception) {
            throw MyException(Constants.EC_GET_WINE, R.string.room_request_fail)
        }
    }

    fun updateWine(wine: Wine, callback: () -> Unit) {
        val result = db.updateWine(wine)
        if (result == 0) {
            throw MyException(Constants.EC_UPDATE_WINE, R.string.room_update_fail)
        }  else {
            callback()
        }
    }
}
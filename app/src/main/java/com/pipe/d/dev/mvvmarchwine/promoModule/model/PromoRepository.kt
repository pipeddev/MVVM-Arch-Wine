package com.pipe.d.dev.mvvmarchwine.promoModule.model

import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.MyException
import com.pipe.d.dev.mvvmarchwine.common.entities.Promo
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants

class PromoRepository(private val db: Database) {
    fun getPromos(): List<Promo> {
        val result = db.getPromos()
        return result.ifEmpty {
            throw MyException(Constants.EC_REQUEST, R.string.promo_request_fail)
        }
    }
}
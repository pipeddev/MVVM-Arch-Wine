package com.pipe.d.dev.mvvmarchwine.promoModule.model

import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.getAllPromos


class Database {
    fun getPromos() = getAllPromos()
}
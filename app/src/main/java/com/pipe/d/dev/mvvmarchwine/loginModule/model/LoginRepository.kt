package com.pipe.d.dev.mvvmarchwine.loginModule.model

import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.FakeFirebaseAuth
import com.pipe.d.dev.mvvmarchwine.common.entities.MyException
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants

class LoginRepository(private val auth: FakeFirebaseAuth) {
    suspend fun checkAuth(): Boolean {
        return auth.isValidAuth()
    }

    suspend fun login(username: String, pin: String): Boolean{
        val result = auth.login(username, pin)
        if (!result) throw MyException(Constants.EC_LOGIN, R.string.login_login_fail)

        return true
    }
}
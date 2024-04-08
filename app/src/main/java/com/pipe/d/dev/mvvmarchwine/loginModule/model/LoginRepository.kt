package com.pipe.d.dev.mvvmarchwine.loginModule.model

import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.FakeFirebaseAuth

class LoginRepository(private val auth: FakeFirebaseAuth) {
    suspend fun checkAuth(): Boolean {
        return auth.isValidAuth()
    }

    suspend fun login(username: String, pin: String): Boolean{
        return auth.login(username, pin)
    }
}
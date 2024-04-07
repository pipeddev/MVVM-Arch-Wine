package com.pipe.d.dev.mvvmarchwine.accountModule.model

import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.FakeFirebaseAuth
import com.pipe.d.dev.mvvmarchwine.common.entities.FirebaseUser

class AccountRepository(private val auth: FakeFirebaseAuth) {
    suspend fun signOut(): Boolean {
        return auth.signOut()
    }

    suspend fun getCurrentUser(): FirebaseUser? {
        return auth.getCurrentUser()
    }
}
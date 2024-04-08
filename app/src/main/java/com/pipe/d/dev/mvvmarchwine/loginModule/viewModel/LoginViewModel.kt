package com.pipe.d.dev.mvvmarchwine.loginModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.common.entities.FirebaseUser
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.loginModule.model.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository): ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _snackBarMsg = MutableLiveData<Int>()
    val snackBarMsg: LiveData<Int> = _snackBarMsg

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _isValidAuth = MutableLiveData<Boolean>()
    val isValidAuth: LiveData<Boolean> = _isValidAuth

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                _isValidAuth.value = repository.checkAuth()
            } finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }

    fun login(username: String, pin: String) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                _isValidAuth.value = repository.login(username, pin)
            } finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
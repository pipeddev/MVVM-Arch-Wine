package com.pipe.d.dev.mvvmarchwine.accountModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.accountModule.model.AccountRepository
import com.pipe.d.dev.mvvmarchwine.common.entities.FirebaseUser
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: AccountRepository): ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _snackBarMsg = MutableLiveData<Int>()
    val snackBarMsg: LiveData<Int> = _snackBarMsg

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _isSignOut = MutableLiveData<Boolean>()
    val isSignOut: LiveData<Boolean> = _isSignOut

    private fun getCurrentUser() {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                _user.value = repository.getCurrentUser()
            }catch (ex: Exception){
                _snackBarMsg.value = R.string.account_sign_out_fail
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
    private fun signOut() {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                _isSignOut.value = repository.signOut()
            }catch (ex: Exception){
                _snackBarMsg.value = R.string.account_request_user_fail
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
package com.pipe.d.dev.mvvmarchwine.favoriteModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.favoriteModule.model.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _snackBarMsg = MutableLiveData<Int>()
    val snackBarMsg: LiveData<Int> = _snackBarMsg

    private val _wines = MutableLiveData<List<Wine>>()
    val wines: LiveData<List<Wine>> = _wines

    private  fun getAllWines() {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                val result = repository.getAllWines()

                result?.let {
                    _wines.value  = result!!
                } ?: {
                    _snackBarMsg.value = R.string.room_request_fail
                }
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }

    private fun addWine(wine: Wine) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                val result = repository.addWine(wine)
                if (result != -1L) {
                    _snackBarMsg.value = R.string.room_save_fail
                } else {
                    _snackBarMsg.value = R.string.room_save_success
                }
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
    private fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                val result = repository.deleteWine(wine)
                if (result == 0) {
                    _snackBarMsg.value = R.string.room_save_fail
                } else {
                    _snackBarMsg.value = R.string.room_save_success
                }
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
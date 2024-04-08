package com.pipe.d.dev.mvvmarchwine.favoriteModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.favoriteModule.model.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _snackBarMsg = MutableLiveData<Int>()
    val snackBarMsg: LiveData<Int> = _snackBarMsg

    private val _wines = MutableLiveData<List<Wine>>()
    val wines: LiveData<List<Wine>> = _wines

    init {
        getAllWines()
    }

    fun getAllWines() {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    val result = repository.getAllWines()

                    result?.let {
                        //_wines.value  = result!!
                        _wines.postValue(result!!)
                    } ?: {
                        _snackBarMsg.postValue(R.string.room_request_fail)
                    }
                }

            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }

    fun addWine(wine: Wine) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    val result = repository.addWine(wine)
                    if (result != -1L) {
                        _snackBarMsg.postValue(R.string.room_save_success)
                    } else {
                        _snackBarMsg.postValue(R.string.room_save_fail)
                    }
                }
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
    fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    val result = repository.deleteWine(wine)
                    if (result == 0) {
                        _snackBarMsg.postValue(R.string.room_save_fail)
                    } else {
                        _snackBarMsg.postValue(R.string.room_save_success)
                    }
                }
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
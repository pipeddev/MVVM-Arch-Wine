package com.pipe.d.dev.mvvmarchwine.updateModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.MyException
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.updateModule.model.UpdateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateViewModel(private val repository: UpdateRepository): ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    private val _snackBarMsg = MutableLiveData<Int>()
    val snackBarMsg: LiveData<Int> = _snackBarMsg

    private val _wine = MutableLiveData<Wine>()
    val wine: LiveData<Wine> = _wine

    fun requestWine(id: Double) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    repository.requestWine(id) {wine ->
                        _wine.postValue(wine)
                    }
                }
            } catch (e: MyException) {
                _snackBarMsg.postValue(e.resMsg)
            } finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }

    fun updateWine(newRating: String) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    _wine.value?.let {
                        it.rating.average = newRating
                        repository.updateWine(it) {
                            _snackBarMsg.postValue(R.string.room_save_success)
                        }
                    }
                }
            } catch (e: MyException) {
                _snackBarMsg.postValue(e.resMsg)
            } finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
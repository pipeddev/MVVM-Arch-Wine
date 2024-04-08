package com.pipe.d.dev.mvvmarchwine.homeModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.MyException
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.homeModule.model.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository): ViewModel() {
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
            viewModelScope.launch {
                _inProgress.value = Constants.SHOW
                try {
                    withContext(Dispatchers.IO) {
                        repository.getAllWines { wines ->
                            _wines.value = wines
                        }
                    }
                }catch (ex: MyException) {
                    _snackBarMsg.postValue(ex.resMsg)
                }finally {
                    _inProgress.value = Constants.HIDE
                }
            }
        }
    }

    fun addWine(wine: Wine) {
        viewModelScope.launch {
            _inProgress.value = Constants.SHOW
            try {
                withContext(Dispatchers.IO) {
                    repository.addWine(wine) {
                        _snackBarMsg.postValue(R.string.room_save_success)
                    }
                }
            }catch (ex: MyException) {
                _snackBarMsg.postValue(ex.resMsg)
            }finally {
                _inProgress.value = Constants.HIDE
            }
        }
    }
}
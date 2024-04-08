package com.pipe.d.dev.mvvmarchwine.loginModule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pipe.d.dev.mvvmarchwine.loginModule.model.LoginRepository

class LoginViewModelFactory(private val repository: LoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((LoginViewModel::class.java))) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida")
    }
}
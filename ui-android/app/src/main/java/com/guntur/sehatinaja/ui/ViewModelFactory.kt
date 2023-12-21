package com.guntur.sehatinaja.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guntur.sehatinaja.data.pref.repository.Repository
import com.guntur.sehatinaja.ui.screen.detail.DetailViewModel
import com.guntur.sehatinaja.ui.screen.home.HomeViewModel
import com.guntur.sehatinaja.ui.screen.login.LoginViewModel
import com.guntur.sehatinaja.ui.screen.register.RegisterViewModel

class ViewModelFactory(private val repos: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(repos) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return  LoginViewModel(repos) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repos) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repos) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
package com.guntur.sehatinaja.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guntur.sehatinaja.data.pref.UserModel
import com.guntur.sehatinaja.data.pref.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repos: Repository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repos.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repos.logout()
        }
    }
}
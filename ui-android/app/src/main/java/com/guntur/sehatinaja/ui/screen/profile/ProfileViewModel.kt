package com.guntur.sehatinaja.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guntur.sehatinaja.data.pref.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repos: Repository): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repos.logout()
        }
    }
}
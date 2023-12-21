package com.guntur.sehatinaja.ui.screen.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.guntur.sehatinaja.data.pref.repository.Repository
import com.guntur.sehatinaja.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repos: Repository): ViewModel() {

    companion object{
        private const val TAG = "registerViewModel"
    }

    private val _registerUser= MutableLiveData<RegisterResponse>()
    val registerUser : LiveData<RegisterResponse> = _registerUser

    fun register(username: String, email: String, password: String, confirmPassword: String){
        viewModelScope.launch {
            try {
                val response = repos.createAccount(username, email, password, confirmPassword)
                _registerUser.value = response
                Log.d(TAG, "onSuccess: ${response.message}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                _registerUser.value = errorBody
                Log.e(TAG, "onFailure: $errorMessage")
            }
        }
    }
}
package com.guntur.sehatinaja.ui.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.guntur.sehatinaja.data.pref.UserModel
import com.guntur.sehatinaja.data.pref.repository.Repository
import com.guntur.sehatinaja.data.remote.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repos: Repository): ViewModel(){

    companion object{
        private const val TAG = "loginViewModel"
    }

    private val _registerUser= MutableLiveData<LoginResponse>()
    val registerUser : LiveData<LoginResponse> = _registerUser

    fun login(username: String, password: String){
        viewModelScope.launch {
            try {
                val response = repos.login(username, password)
                saveUser(
                    UserModel(
                        response.loginResult?.userId!!,
                        response.loginResult.name!!,
                        response.loginResult.token!!,
                        true
                    )
                )
                _registerUser.value = response
                Log.d(TAG, "onSuccess: ${response.message}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                _registerUser.value = errorBody
                Log.e(TAG, "onFailure: $errorMessage")
            }
        }
    }
    private fun saveUser(user: UserModel){
        viewModelScope.launch {
            repos.saveUser(user)
        }
    }
}
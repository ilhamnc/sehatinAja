package com.guntur.sehatinaja.data.pref.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.guntur.sehatinaja.data.pref.UserModel
import com.guntur.sehatinaja.data.pref.UserPreference
import com.guntur.sehatinaja.data.remote.retrofit.ApiService

class Repository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun createAccount(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = apiService.createAccount(username, email, password, confirmPassword)

    suspend fun login(
        username: String,
        password: String
    ) = apiService.loginAccount(username, password)

    suspend fun predictData(
        age: Float,
        weight: Float,
        height: Float
    ) = apiService.predictData(age, weight, height)


    suspend fun saveUser(user: UserModel){
        userPreference.saveSession(user)
    }

    fun getSession(): LiveData<UserModel> {
        return userPreference.getSession().asLiveData()
    }

    suspend fun logout() {
        userPreference.logout()
    }
    companion object{
        @Volatile
        private var instance: Repository? = null
        fun clearInstance() {
            instance = null
        }
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): Repository =
            instance ?: synchronized(this){
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }
}
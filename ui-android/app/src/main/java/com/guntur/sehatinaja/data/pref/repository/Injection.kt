package com.guntur.sehatinaja.data.pref.repository

import android.content.Context
import com.guntur.sehatinaja.data.pref.UserPreference
import com.guntur.sehatinaja.data.pref.dataStore
import com.guntur.sehatinaja.data.remote.retrofit.ApiConfig
import com.guntur.sehatinaja.data.remote.retrofit.ApiType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context, apiType: ApiType): Repository = runBlocking {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = userPreference.getSession().first()
        val apiService = ApiConfig.getApiService(user.token, apiType)
        Repository.getInstance(userPreference, apiService)
    }
}
package com.guntur.sehatinaja.ui.screen.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.guntur.sehatinaja.data.pref.repository.Repository
import com.guntur.sehatinaja.data.remote.response.MlResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val repos: Repository): ViewModel() {
    companion object{
        private const val TAG = "detailViewModel"
    }

    private val _predict = MutableLiveData<MlResponse>()
    val predict: LiveData<MlResponse> = _predict

    fun getPredict(age: Float, weight: Float, height: Float){
        viewModelScope.launch {
            try {
                val response = repos.predictData(age, weight, height)
                _predict.value = response
                Log.d(TAG, "onSuccess ${response.message}")
            } catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MlResponse::class.java)
                val errorMessage = errorBody.message
                _predict.value = errorBody
                Log.e(TAG, "onFailure: $errorMessage")
            }
        }
    }
}
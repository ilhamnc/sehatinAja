package com.guntur.sehatinaja.data.remote.retrofit

import com.guntur.sehatinaja.data.remote.response.LoginResponse
import com.guntur.sehatinaja.data.remote.response.MlResponse
import com.guntur.sehatinaja.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun createAccount(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun loginAccount(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("predict")
    suspend fun predictData(
        @Field("age") age: Float,
        @Field("weight") weight: Float,
        @Field("height") height: Float
    ): MlResponse
}
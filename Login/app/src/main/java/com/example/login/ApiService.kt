package com.example.login

import com.example.login.data.LoginRequest
import com.example.login.data.RegisterRequest
import com.example.login.data.TokenResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/user/login")
    suspend fun login(@Body credentials: LoginRequest): Response<TokenResponse.Data>

    @POST("/user/save")
    suspend fun save(@Body userInfo: RegisterRequest): Response<Void>
}

object ApiClient {
    private const val BASE_URL = "http://192.168.31.186:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
package com.kotlin.aplantgenius.data

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @Headers("Content-Type: application/json")
    @POST("/fungsi")
    fun scanImage(
        @Header("Authorization") token: String,
        @Body request: ImageRequest
    ): Call<ImageResponse>
}
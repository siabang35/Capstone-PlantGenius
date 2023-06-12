package com.kotlin.aplantgenius.data

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @Multipart
    @POST("/fungsi")
    fun scanImage(
        @Header("Authorization") token: String?,
        @Part("imageBase64") imageBase64: String?
    ): Call<ImageResponse>

}

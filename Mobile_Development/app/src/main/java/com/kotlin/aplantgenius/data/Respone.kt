package com.kotlin.aplantgenius.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val emailPhone: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("token")
    val token: String
)

data class LoginErrorResponse(
    @SerializedName("message")
    val message: String
)

data class RegisterRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("no_telepon")
    val phone: String
)

data class RegisterResponse(
    @SerializedName("User")
    val user: User
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("no_telepon")
    val phone: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("createdAt")
    val createdAt: String
)

data class ErrorRegister(
    @SerializedName("message")
    val message: String,

    @SerializedName("details")
    val details: ErrorDetails
)

data class ErrorDetails(
    @SerializedName("email")
    val email: ErrorMessage?,

    @SerializedName("no_telepon")
    val phone: List<ErrorMessage>?,

    @SerializedName("name")
    val name: ErrorMessage?,

    @SerializedName("password")
    val password: ErrorMessage?
)

data class ErrorMessage(
    @SerializedName("message")
    val message: String
)

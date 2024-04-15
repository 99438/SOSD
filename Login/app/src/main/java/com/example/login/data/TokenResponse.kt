package com.example.login.data


data class TokenResponse(
    val code: Int,
    val msg: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val status: Int,
        val token: String // token字符串
    )
}
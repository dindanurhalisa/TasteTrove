package com.example.tastetrove.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
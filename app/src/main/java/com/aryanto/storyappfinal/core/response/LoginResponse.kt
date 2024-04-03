package com.aryanto.storyappfinal.core.response

import com.aryanto.storyappfinal.core.model.LoginResult

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)

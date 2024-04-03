package com.aryanto.storyappfinal.core.data.response

import com.aryanto.storyappfinal.core.data.model.LoginResult

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)

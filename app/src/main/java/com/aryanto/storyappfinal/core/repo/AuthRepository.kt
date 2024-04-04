package com.aryanto.storyappfinal.core.repo

import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.LoginResponse
import com.aryanto.storyappfinal.core.data.response.RegisterResponse

class AuthRepository(
    private val apiService: ApiService
) {

    suspend fun login(email: String, pass: String): LoginResponse {
        return apiService.login(email, pass)
    }

    suspend fun register(name: String, email: String, pass: String): RegisterResponse {
        return apiService.register(name, email, pass)
    }

}
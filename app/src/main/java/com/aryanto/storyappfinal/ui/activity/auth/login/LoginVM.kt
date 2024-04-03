package com.aryanto.storyappfinal.ui.activity.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyappfinal.core.data.model.LoginResult
import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.LoginResponse
import com.aryanto.storyappfinal.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _login = MutableLiveData<ClientState<LoginResult>>()
    val login: LiveData<ClientState<LoginResult>> = _login

    fun performLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                _login.postValue(ClientState.LOADING())
                val response = apiService.login(email, password)

                if (response.error) {
                    _login.postValue(ClientState.ERROR(response.message))
                } else {
                    _login.postValue(ClientState.SUCCESS(response.loginResult!!))
                }
            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _login.postValue(ClientState.ERROR(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, LoginResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _login.postValue(ClientState.ERROR(errorMSG))
        } catch (e: Exception) {
            _login.postValue(ClientState.ERROR("Error when parsing response msg", null))
        }

    }

}
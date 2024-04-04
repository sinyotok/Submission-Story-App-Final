package com.aryanto.storyappfinal.ui.activity.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyappfinal.core.data.response.RegisterResponse
import com.aryanto.storyappfinal.core.repo.AuthRepository
import com.aryanto.storyappfinal.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterVM(
    private val authRepo: AuthRepository
) : ViewModel() {
    private val _register = MutableLiveData<ClientState<RegisterResponse>>()
    val register: LiveData<ClientState<RegisterResponse>> = _register

    fun performRegister(name: String, email: String, pass: String) {
        viewModelScope.launch {
            try {
                _register.postValue(ClientState.LOADING())
                val response = authRepo.register(name, email, pass)

                if (response.error) {
                    _register.postValue(ClientState.ERROR(response.message))
                } else {
                    _register.postValue(ClientState.SUCCESS(response))
                }

            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _register.postValue(ClientState.ERROR(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, RegisterResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _register.postValue(ClientState.ERROR(errorMSG))
        } catch (e: Exception) {
            _register.postValue(ClientState.ERROR("Error when parsing response msg", null))
        }
    }

}
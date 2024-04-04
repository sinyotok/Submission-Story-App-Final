package com.aryanto.storyappfinal.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.DetailResponse
import com.aryanto.storyappfinal.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _detail = MutableLiveData<ClientState<Story>>()
    val detail: LiveData<ClientState<Story>> = _detail

    fun detail(storyId: String) {
        viewModelScope.launch {
            try {
                _detail.postValue(ClientState.LOADING())
                val response = apiService.getDetail(storyId)

                if (response.error) {
                    _detail.postValue(ClientState.ERROR(response.message))
                } else {
                    _detail.postValue(ClientState.SUCCESS(response.story))
                }

            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _detail.postValue(ClientState.ERROR(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, DetailResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _detail.postValue(ClientState.ERROR(errorMSG))
        } catch (e: Exception) {
            _detail.postValue(ClientState.ERROR("Error when parsing response msg", null))
        }

    }
}
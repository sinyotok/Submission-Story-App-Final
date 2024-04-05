package com.aryanto.storyappfinal.ui.activity.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.response.StoryResponse
import com.aryanto.storyappfinal.core.repo.AppRepository
import com.aryanto.storyappfinal.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MapVM(
    private val appRepo: AppRepository
) : ViewModel() {
    private val _map = MutableLiveData<ClientState<List<Story>>>()
    val map: LiveData<ClientState<List<Story>>> = _map

    fun performLogin() {
        viewModelScope.launch {
            try {
                _map.postValue(ClientState.LOADING())
                val response = appRepo.getLocation()

                if (response.error) {
                    _map.postValue(ClientState.ERROR(response.message))
                } else {
                    _map.postValue(ClientState.SUCCESS(response.listStory))
                }
            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _map.postValue(ClientState.ERROR(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, StoryResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _map.postValue(ClientState.ERROR(errorMSG))
        } catch (e: Exception) {
            _map.postValue(ClientState.ERROR("Error when parsing response msg", null))
        }

    }
}
package com.aryanto.storyappfinal.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiClient
import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.StoryResponse
import com.aryanto.storyappfinal.core.repo.AppRepository
import com.aryanto.storyappfinal.utils.ClientState
import com.aryanto.storyappfinal.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeVM(
    storyRepository: AppRepository
) : ViewModel() {

    val story: LiveData<PagingData<Story>> = storyRepository.getStory()

//    private val _stories = MutableLiveData<ClientState<List<Story>>>()
//    val stories: LiveData<ClientState<List<Story>>> = _stories
//
//    fun getStories() {
//        viewModelScope.launch {
//            try {
//                _stories.postValue(ClientState.LOADING())
//
//                val auth = tManager.getToken() ?: ""
//                ApiClient.setAuthToken(auth)
//
//                val response = apiService.getStories(page = 1, size = 20, location = 1)
//
//                if (response.error) {
//                    _stories.postValue(ClientState.ERROR(response.message))
//                } else {
//                    _stories.postValue(ClientState.SUCCESS(response.listStory))
//                }
//
//            } catch (he: HttpException) {
//                handleHttpException(he)
//            } catch (e: Exception) {
//                val errorMSG = when (e) {
//                    is IOException -> "${e.message}"
//                    else -> "Unknown error: ${e.message}"
//                }
//                _stories.postValue(ClientState.ERROR(errorMSG))
//            }
//        }
//    }
//
//    private fun handleHttpException(he: HttpException) {
//        val jsonString = he.response()?.errorBody()?.string()
//        try {
//            val errorResponse = Gson().fromJson(jsonString, StoryResponse::class.java)
//            val errorMSG = errorResponse?.message ?: "Unknown error"
//            _stories.postValue(ClientState.ERROR(errorMSG))
//        } catch (e: Exception) {
//            _stories.postValue(ClientState.ERROR("Error when parsing response msg", null))
//        }
//
//    }
}
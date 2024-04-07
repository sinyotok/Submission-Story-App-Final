package com.aryanto.storyappfinal.core.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.aryanto.storyappfinal.core.data.PagingSource
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.AddStoryResponse
import com.aryanto.storyappfinal.core.data.response.DetailResponse
import com.aryanto.storyappfinal.core.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(
    private val apiService: ApiService
) {

    suspend fun detail(id: String): DetailResponse {
        return apiService.getDetail(id)
    }

    suspend fun getLocation(): StoryResponse{
        return apiService.getStoriesLocation()
    }

    suspend fun upload(
        img: MultipartBody.Part,
        desc: RequestBody,
        lat: Double,
        lon: Double
    ): AddStoryResponse{
        return apiService.uploadStory(img, desc, lat, lon)
    }

    fun getStory(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PagingSource(apiService)
            }
        ).liveData
    }

}
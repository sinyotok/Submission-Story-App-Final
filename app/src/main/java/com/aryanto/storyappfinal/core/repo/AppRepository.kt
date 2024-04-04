package com.aryanto.storyappfinal.core.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.aryanto.storyappfinal.core.data.PagingSource
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiService
import com.aryanto.storyappfinal.core.data.response.DetailResponse

class AppRepository(
    private val apiService: ApiService
) {

    suspend fun detail(id: String): DetailResponse {
        return apiService.getDetail(id)
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
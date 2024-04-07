package com.aryanto.storyappfinal.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiService

class PagingSource(
    private val apiService: ApiService
): PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { ancPos->
            val ancPage = state.closestPageToPosition(ancPos)
            ancPage?.prevKey?.plus(1) ?: ancPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getStories(page, params.loadSize, 1)
            val data =  response.listStory

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (data.isEmpty()) null else page +1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
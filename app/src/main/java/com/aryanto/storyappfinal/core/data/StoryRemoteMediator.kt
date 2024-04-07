package com.aryanto.storyappfinal.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.data.network.ApiService

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val apiService: ApiService
) : RemoteMediator<Int, Story>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Story>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.let { story ->
                    story.id.toIntOrNull()?.plus(1) ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                } ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = apiService.getStories(page, state.config.pageSize, null)
            if (response.error) {
                MediatorResult.Error(Exception(response.message))
            } else {
                val story = response.listStory
                MediatorResult.Success(endOfPaginationReached = story.isEmpty())
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }
}
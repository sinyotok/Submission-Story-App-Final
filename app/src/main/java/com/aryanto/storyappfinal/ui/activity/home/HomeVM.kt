package com.aryanto.storyappfinal.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.repo.AppRepository

class HomeVM(
    storyRepository: AppRepository
) : ViewModel() {

    val story: LiveData<PagingData<Story>> = storyRepository.getStory().cachedIn(viewModelScope)

}
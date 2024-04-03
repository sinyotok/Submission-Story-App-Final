package com.aryanto.storyappfinal.core.data.response

import com.aryanto.storyappfinal.core.data.model.Story

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>
)

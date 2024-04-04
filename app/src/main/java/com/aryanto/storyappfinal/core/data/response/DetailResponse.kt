package com.aryanto.storyappfinal.core.data.response

import com.aryanto.storyappfinal.core.data.model.Story

data class DetailResponse(
    val error: Boolean,
    val message: String,
    val story: Story
)

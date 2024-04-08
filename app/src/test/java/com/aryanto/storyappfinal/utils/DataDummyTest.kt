package com.aryanto.storyappfinal.utils

import com.aryanto.storyappfinal.core.data.model.Story

object DataDummyTest {
    fun gnrDummyListStory(): List<Story> {
        val storyList: MutableList<Story> = arrayListOf()
        for (i in 0..10) {
            val stories = Story(
                "id $i",
                "biliboo",
                "testing testing aja lohh",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                "0.0f".toDouble(),
                "0.0f".toDouble()
            )
            storyList.add(stories)
        }
        return storyList
    }
}
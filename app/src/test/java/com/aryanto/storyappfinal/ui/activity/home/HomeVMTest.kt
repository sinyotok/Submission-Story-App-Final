package com.aryanto.storyappfinal.ui.activity.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.core.repo.AppRepository
import com.aryanto.storyappfinal.utils.DataDummyTest
import com.aryanto.storyappfinal.utils.MainDispatcherRule
import com.aryanto.storyappfinal.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeVMTest {

    @get:Rule
    val instantExecutable = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var appRepo: AppRepository

    @Test
    fun `when get story return success`() = runTest {
        val dummyStory = DataDummyTest.gnrDummyListStory()
        val data: PagingData<Story> = StoryPS.snapShot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<Story>>()

        expectedStory.value = data
        `when`(appRepo.getStory()).thenReturn(expectedStory)

        val homeVM = HomeVM(appRepo)
        val actualStory: PagingData<Story> = homeVM.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingAdapter.myDiffCB,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0], differ.snapshot()[0])

    }

    @Test
    fun `when story empty return null`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<Story>>()

        expectedStory.value = data
        `when`(appRepo.getStory()).thenReturn(expectedStory)

        val homeVM = HomeVM(appRepo)
        val actualStory: PagingData<Story> = homeVM.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingAdapter.myDiffCB,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertEquals(0, differ.snapshot().size)

    }

}

class StoryPS : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapShot(story: List<Story>): PagingData<Story> {
            return PagingData.from(story)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}

    override fun onRemoved(position: Int, count: Int) {}

    override fun onMoved(fromPosition: Int, toPosition: Int) {}

    override fun onChanged(position: Int, count: Int, payload: Any?) {}

}
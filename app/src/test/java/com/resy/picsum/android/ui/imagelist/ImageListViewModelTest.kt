package com.resy.picsum.android.ui.imagelist

import com.resy.picsum.android.R
import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.data.model.ImageListResult
import com.resy.picsum.domain.usecase.GetImageListUseCase
import com.resy.picsum.framework.android.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.Thread.sleep
import java.net.UnknownHostException

class ImageListViewModelTest {
    private val resourceProvider = object: ResourceProvider {
        override fun getString(resId: Int): String {
            return resId.toString()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testErrorAfterLoading() = runTest {
        val loadingThenErrorUseCase = object:GetImageListUseCase {
            override suspend fun invoke() = flow {
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf()
                    )
                )
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                throw UnknownHostException()
            }
        }

        val viewModel = ImageListViewModel(
            getImageListUseCase = loadingThenErrorUseCase,
            resourceProvider = resourceProvider
        )

        val states = viewModel.state.take(2).toList()

        assertTrue("First state should be loading state", states[0] is ImageListState.ImageListStateLoading)
        assertTrue("Second state should be error state", states[1] is ImageListState.ImageListStateError)
    }

    @Test
    fun testErrorAfterSuccess() = runTest {
        val loadingThenErrorUseCase = object:GetImageListUseCase {
            override suspend fun invoke() = flow {
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf(
                            Image(0, 2000, 3000, "0.jpg", "John Doe")
                        )
                    )
                )
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                throw UnknownHostException()
            }
        }

        val viewModel = ImageListViewModel(
            getImageListUseCase = loadingThenErrorUseCase,
            resourceProvider = resourceProvider
        )

        val states = viewModel.state.take(3).toList()

        assertTrue(
            "First state should be loading state",
            states[0] is ImageListState.ImageListStateLoading
        )
        assertTrue(
            "Second state should be success state",
            states[1] is ImageListState.ImageListStateSuccess
        )
        assertNull(
            "Second state should not have error",
            (states[1] as ImageListState.ImageListStateSuccess).errorMessage
        )
        assertTrue(
            "Third state should be success state",
            states[2] is ImageListState.ImageListStateSuccess
        )
        assertEquals(
            "Third state should have error",
            Event(R.string.error_loading.toString()),
            (states[2] as ImageListState.ImageListStateSuccess).errorMessage
        )
    }

    @Test
    fun testSuccessAfterLoading() = runTest {
        val loadingThenErrorUseCase = object:GetImageListUseCase {
            override suspend fun invoke() = flow {
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf()
                    )
                )
                withContext(Dispatchers.IO) {
                    sleep(200)
                }
                emit(
                    ImageListResult(
                        datasource = Datasource.REMOTE,
                        result = listOf(
                            Image(0, 2000, 3000, "0.jpg", "John Doe")
                        )
                    )
                )
            }
        }

        val viewModel = ImageListViewModel(
            getImageListUseCase = loadingThenErrorUseCase,
            resourceProvider = resourceProvider
        )

        val states = viewModel.state.take(2).toList()

        assertTrue(
            "First state should be loading state",
            states[0] is ImageListState.ImageListStateLoading
        )
        assertTrue(
            "Second state should be success state",
            states[1] is ImageListState.ImageListStateSuccess
        )
        assertNull(
            "Second state should not have error",
            (states[1] as ImageListState.ImageListStateSuccess).errorMessage
        )
    }
}

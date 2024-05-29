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
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ImageViewModelTest {
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
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf()
                    )
                )
                throw UnknownHostException()
            }
        }

        val viewModel = ImageListViewModel(
            getImageListUseCase = loadingThenErrorUseCase,
            resourceProvider = resourceProvider,
            navigateToImage = {}
        )

        val state = viewModel.state.value

        assertTrue("State should be error state $state", state is ImageListState.ImageListStateError)
    }

    @Test
    fun testErrorAfterSuccess() = runTest {
        val loadingThenErrorUseCase = object:GetImageListUseCase {
            override suspend fun invoke() = flow {
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf(
                            Image(0, 2000, 3000, "0.jpg", "John Doe")
                        )
                    )
                )
                throw UnknownHostException()
            }
        }

        val viewModel = ImageListViewModel(
            getImageListUseCase = loadingThenErrorUseCase,
            resourceProvider = resourceProvider,
            navigateToImage = {}
        )

        val state = viewModel.state.value

        assertTrue(
            "State should be success state",
            state is ImageListState.ImageListStateSuccess
        )
        assertEquals(
            "State should have error",
            Event(R.string.error_loading.toString()),
            (state as ImageListState.ImageListStateSuccess).errorMessage
        )
    }

    @Test
    fun testSuccessAfterLoading() = runTest {
        val loadingThenErrorUseCase = object:GetImageListUseCase {
            override suspend fun invoke() = flow {
                emit(
                    ImageListResult(
                        datasource = Datasource.LOCAL,
                        result = listOf()
                    )
                )
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
            resourceProvider = resourceProvider,
            navigateToImage = {}
        )

        val state = viewModel.state.value

        assertTrue(
            "State should be success state",
            state is ImageListState.ImageListStateSuccess
        )
        assertNull(
            "State should not have error",
            (state as ImageListState.ImageListStateSuccess).errorMessage
        )
    }
}

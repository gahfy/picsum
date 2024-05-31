package com.resy.picsum.android.ui.image

import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.data.model.Image
import com.resy.picsum.domain.usecase.GetCachedFileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ImageViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testPortraitForVeryLongScreen() = runTest {
        val image = Image(1, 1000, 2000, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                return CachedFile(
                    "https://someurl/someFile.jpeg",
                    10L,
                    "hgjfkdfss.jpeg"
                )
            }
        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        viewModel.state.value.onSizeAvailable(
            1000,
            6000,
            0.5f
        )

        assertTrue("State should be success state", viewModel.state.value is ImageState.ImageSuccessState)
        val state = viewModel.state.value as ImageState.ImageSuccessState
        assertEquals("Orientation should be portrait", Orientation.PORTRAIT, state.orientation)
    }

    @Test
    fun testLandscapeForVeryLongScreen() = runTest {
        val image = Image(1, 1000, 500, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                return CachedFile(
                    "https://someurl/someFile.jpeg",
                    10L,
                    "hgjfkdfss.jpeg"
                )
            }

        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        viewModel.state.value.onSizeAvailable(
            1000,
            6000,
            0.5f
        )

        assertTrue("State should be success state", viewModel.state.value is ImageState.ImageSuccessState)
        val state = viewModel.state.value as ImageState.ImageSuccessState
        assertEquals("Orientation should be landscape", Orientation.LANDSCAPE, state.orientation)
    }

    @Test
    fun testLandscapeForVeryShortScreen() = runTest {
        val image = Image(1, 1000, 999, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                return CachedFile(
                    "https://someurl/someFile.jpeg",
                    10L,
                    "hgjfkdfss.jpeg"
                )
            }

        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        viewModel.state.value.onSizeAvailable(
            1000,
            1001,
            0.5f
        )

        assertTrue("State should be success state", viewModel.state.value is ImageState.ImageSuccessState)
        val state = viewModel.state.value as ImageState.ImageSuccessState
        assertEquals("Orientation should be portrait", Orientation.PORTRAIT, state.orientation)
    }

    @Test
    fun testNoSecondDownloadIfHigherImageExists() {
        var numberOfDownloads = 0
        val image = Image(1, 1000, 2000, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                numberOfDownloads++
                return CachedFile(
                    "https://someurl/someFile.jpeg",
                    10L,
                    "hgjfkdfss.jpeg"
                )
            }
        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        // Simulate screen in landscape mode
        viewModel.state.value.onSizeAvailable(
            1000,
            500,
            0.5f
        )
        // Simulate switch to portrait
        viewModel.state.value.onSizeAvailable(
            500,
            1000,
            0.5f
        )

        assertEquals("Image should have been downloaded once only", 1, numberOfDownloads)
    }

    @Test
    fun testSecondDownloadIfLowerImageExists() {
        var numberOfDownloads = 0
        val image = Image(1, 1000, 2000, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                numberOfDownloads++
                return CachedFile(
                    "https://someurl/someFile.jpeg",
                    10L,
                    "hgjfkdfss.jpeg"
                )
            }
        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        // Simulate screen in landscape mode
        viewModel.state.value.onSizeAvailable(
            500,
            1000,
            0.5f
        )
        // Simulate switch to portrait
        viewModel.state.value.onSizeAvailable(
            1000,
            500,
            0.5f
        )

        assertEquals("Image should have been downloaded twice only", 2, numberOfDownloads)
    }

    @Test
    fun testErrorState() {
        val image = Image(1, 1000, 2000, "someFile.jpeg", "John Doe", "jpeg")
        val useCase = object: GetCachedFileUseCase {
            override suspend fun invoke(url: String, type: String): CachedFile {
                throw IOException("Some exception")
            }
        }

        val viewModel = ImageViewModel(
            image,
            useCase
        )

        viewModel.state.value.onSizeAvailable(
            1000,
            6000,
            0.5f
        )

        assertTrue("State should be error state", viewModel.state.value is ImageState.ImageErrorState)
    }
}

package com.resy.picsum.android.ui.imagelist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.resy.picsum.android.ui.MainActivity
import com.resy.picsum.framework.api.model.ApiImage
import com.resy.picsum.framework.api.service.PicsumApiService
import com.resy.picsum.framework.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@UninstallModules(
    NetworkModule::class
)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ImageListScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun testMainScreen() {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText("test.jpeg").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("test.jpeg").performClick()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeBaseUrlModule {

        @Provides
        @Singleton
        fun providePicsumApiService(): PicsumApiService = object: PicsumApiService {
            override suspend fun getImageList(): List<ApiImage> {
                return listOf(
                    ApiImage(
                        id = 0,
                        format = "jpeg",
                        width = 100,
                        height = 100,
                        filename = "test.jpeg",
                        author = "John Doe",
                        authorUrl = "https://www.someurl.com",
                        postUrl = "https://www.someurl.com"
                    )
                )
            }

        }
    }
}
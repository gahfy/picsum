package com.resy.picsum.android.ui.imagelist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import com.resy.picsum.data.model.Image
import org.junit.Rule
import org.junit.Test

class ImageListScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun testMainScreen() {
        composeTestRule.setContent {
            AppTheme {
                AppSurface {
                    ImageListScreen(
                        ImageListState.ImageListStateSuccess(
                            images = listOf(
                                Image(0, 3000, 4000, "0.jpg", "John Doe", "jpeg")
                            ),
                            errorMessage = null,
                            onErrorActionClick = {},
                        ),
                        onNavigateToImage = {}
                    )
                }
            }
        }
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
    }
}
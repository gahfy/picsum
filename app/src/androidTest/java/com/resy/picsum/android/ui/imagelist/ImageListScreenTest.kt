package com.resy.picsum.android.ui.imagelist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import com.resy.picsum.data.model.Image
import org.junit.Rule
import org.junit.Test

/**
 * This class contains the list of tests for the main screen
 */
class MainScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    /**
     * Checks that the main screen is displaying an Hello, World! message
     */
    @Test
    fun testMainScreen() {
        composeTestRule.setContent {
            AppTheme {
                AppSurface {
                    ImageListScreen(
                        ImageListState.ImageListStateSuccess(
                            images = listOf(
                                Image(0, 3000, 4000, "0.jpg", "John Doe")
                            ),
                            errorMessage = null,
                            onErrorActionClick = {}
                        )
                    )
                }
            }
        }
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
    }
}
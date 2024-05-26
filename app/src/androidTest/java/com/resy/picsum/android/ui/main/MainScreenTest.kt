package com.resy.picsum.android.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
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
                    MainScreen()
                }
            }
        }

        composeTestRule.onNodeWithText("Hello, World!").assertIsDisplayed()
    }
}
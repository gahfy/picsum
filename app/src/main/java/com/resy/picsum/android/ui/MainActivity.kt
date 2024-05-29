package com.resy.picsum.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.resy.picsum.android.ui.navigation.AppNavigation
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity of the app. As this is a single Activity architecture, this is the only activity
 * of the application.
 */
@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    /**
     * @inheritDoc
     * This essentially displays the content of the main Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppSurface {
                    AppNavigation()
                }
            }
        }
    }
}

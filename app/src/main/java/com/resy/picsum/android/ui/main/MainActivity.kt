package com.resy.picsum.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.google.firebase.BuildConfig
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity of the app. As this is a single Activity architecture, this is the only activity
 * of the application.
 */
@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    val viewmodel: MainViewModel by viewModels()
    /**
     * @inheritDoc
     * This essentially displays the content of the main Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AppSurface {
                    MainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }

        viewmodel.launch()
    }
}

package com.resy.picsum.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.resy.picsum.android.ui.imagelist.ImageListScreen
import com.resy.picsum.android.ui.imagelist.ImageListViewModel
import com.resy.picsum.android.ui.theme.AppSurface
import com.resy.picsum.android.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Main Activity of the app. As this is a single Activity architecture, this is the only activity
 * of the application.
 */
@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val viewmodel: ImageListViewModel by viewModels()
    /**
     * @inheritDoc
     * This essentially displays the content of the main Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.state.collect { mainState ->
                    setContent {
                        AppTheme {
                            AppSurface {
                                ImageListScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    state = mainState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

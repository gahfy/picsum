package com.resy.picsum.android

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application class of the application. In use for dagger only for now.
 *
 * @constructor Instantiates a new [PicsumApplication].
 */
@HiltAndroidApp
class PicsumApplication: Application() {
    init {
            if(BuildConfig.DEBUG)
                StrictMode.enableDefaults()
    }
}

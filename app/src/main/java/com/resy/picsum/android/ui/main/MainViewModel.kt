package com.resy.picsum.android.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resy.picsum.data.repository.ImageRepository
import com.resy.picsum.domain.usecase.GetImageListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel which notifies the view about the state of the main screen.
 *
 * @property getImageListUseCase The use case to get the list of images
 *
 * @constructor Instantiates a new [MainViewModel].
 *
 * @param getImageListUseCase The use case to get the list of images to set
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase
): ViewModel() {

    /**
     * To be called when the view is launched.
     */
    fun launch() {
        viewModelScope.launch {
            getImageListUseCase().collect {
                Log.e("Gahfy", "$it")
            }
        }
    }
}

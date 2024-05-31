package com.resy.picsum.android.ui.imagelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resy.picsum.android.BuildConfig
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.data.model.ImageListResult
import com.resy.picsum.domain.usecase.GetImageListUseCase
import com.resy.picsum.framework.android.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel which notifies the view about the state of the image list screen.
 *
 * @property getImageListUseCase The use case to get the list of images
 * @property resourceProvider    The provider to use for getting resources of the application
 * @property _state The current state of the view, which can be edited by the ViewModel
 * @property state The current state of the view that can be subscribed outside of the ViewModel
 *
 * @constructor Instantiates a new [ImageListViewModel].
 *
 * @param getImageListUseCase The use case to get the list of images to set
 * @param resourceProvider    The provider to use for getting resources of the application to set
 */
@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private val _state: MutableState<ImageListState> = mutableStateOf(
        ImageListState.ImageListStateLoading
    )
    val state: State<ImageListState> = _state

    init {
        loadImages()
    }

    /**
     * Loads the list of images.
     */
    private fun loadImages() {
        setLoadImageStartState()
        viewModelScope.launch {
            try {
                getImageListUseCase().collect {result ->
                    updateOnImageState(result)
                }
            } catch(e: IOException) {
                if(BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                setOnErrorState()
            }
        }
    }

    /**
     * Sets the state for loading according to the current state.
     *
     * If it has some images to display, then it will show the list of images. Otherwise, it will
     * show the loading screen.
     */
    private fun setLoadImageStartState() {
        if(_state.value is ImageListState.ImageListStateSuccess) {
            updateSuccessState()
        } else {
            updateLoadingState()
        }
    }

    /**
     * Sets the state when an error occurs according to the current state.
     *
     * If it has some images to display, then it will show an error snackbar. Otherwise, it will
     * show a full error screen.
     */
    private fun setOnErrorState() {
        val stateValue = _state.value
        if(stateValue is ImageListState.ImageListStateSuccess) {
            updateSuccessState(errorMessage = Event(
                resourceProvider.getString(R.string.error_loading)
            ))
        } else {
            updateErrorState()
        }
    }

    /**
     * Sets the state when images are updated.
     *
     * If it has images to display, it will display them. Otherwise, if the empty list comes from
     * local datasource, then loading state will be kept, otherwise, we will show an empty screen.
     *
     * @param result the result retrieved from the use case
     */
    private fun updateOnImageState(
        result: ImageListResult
    ) {
        if(result.result.isNotEmpty()) {
            updateSuccessState(images = result.result)
        } else {
            if(result.datasource == Datasource.LOCAL) {
                updateLoadingState()
            } else {
                updateSuccessState(images = result.result)
            }
        }
    }

    /**
     * Updates the success state.
     *
     * @param images       the list of images of the state.
     * @param errorMessage the error message of the state
     */
    private fun updateSuccessState(
        images: List<Image> =
            (_state.value as? ImageListState.ImageListStateSuccess)?.images?: listOf(),
        errorMessage: Event<String>? =
            (_state.value as? ImageListState.ImageListStateSuccess)?.errorMessage
    ) {
        _state.value = ImageListState.ImageListStateSuccess(
            images,
            errorMessage,
            ::loadImages
        )
    }

    /**
     * Updates the error state.
     */
    private fun updateErrorState() {
        _state.value = ImageListState.ImageListStateError(
            onErrorActionClick = ::loadImages
        )
    }

    /**
     * Updates the loading state.
     */
    private fun updateLoadingState() {
        _state.value = ImageListState.ImageListStateLoading
    }
}

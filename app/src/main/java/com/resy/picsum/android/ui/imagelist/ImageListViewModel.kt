package com.resy.picsum.android.ui.imagelist

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel which notifies the view about the state of the image list screen.
 *
 * @property getImageListUseCase The use case to get the list of images
 *
 * @constructor Instantiates a new [ImageListViewModel].
 *
 * @param getImageListUseCase The use case to get the list of images to set
 */
@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private val _state: MutableStateFlow<ImageListState> = MutableStateFlow(
        ImageListState.ImageListStateLoading
    )
    val state: StateFlow<ImageListState> = _state


    /**
     * To be called when the view is launched.
     */
    init {
        loadImages()
    }

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

    private fun setLoadImageStartState() {
        if(_state.value is ImageListState.ImageListStateSuccess) {
            updateSuccessState()
        } else {
            updateLoadingState()
        }
    }

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

    private fun updateSuccessState(
        images: List<Image> =
            (_state.value as? ImageListState.ImageListStateSuccess)?.images?: listOf(),
        errorMessage: Event<String>? =
            (_state.value as? ImageListState.ImageListStateSuccess)?.errorMessage,
        onErrorActionClick: () -> Unit =
            (_state.value as? ImageListState.ImageListStateSuccess)?.onErrorActionClick ?: ::loadImages
    ) {
        _state.value = ImageListState.ImageListStateSuccess(
            images,
            errorMessage,
            onErrorActionClick
        )
    }

    private fun updateErrorState(
        onErrorActionClick: () -> Unit =
            (_state.value as? ImageListState.ImageListStateError)?.onErrorActionClick ?: ::loadImages
    ) {
        _state.value = ImageListState.ImageListStateError(
            onErrorActionClick = onErrorActionClick
        )
    }

    private fun updateLoadingState() {
        _state.value = ImageListState.ImageListStateLoading
    }
}

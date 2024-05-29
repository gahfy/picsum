package com.resy.picsum.android.ui.imagelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.resy.picsum.android.BuildConfig
import com.resy.picsum.android.R
import com.resy.picsum.android.ui.model.Event
import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.data.model.ImageListResult
import com.resy.picsum.domain.usecase.GetImageListUseCase
import com.resy.picsum.framework.android.ResourceProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel which notifies the view about the state of the image list screen.
 *
 * @property getImageListUseCase The use case to get the list of images
 *
 * @constructor Instantiates a new [ImageListViewModel].
 *
 * @param getImageListUseCase The use case to get the list of images to set
 */
@HiltViewModel(assistedFactory = ImageListViewModel.Factory::class)
class ImageListViewModel @AssistedInject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val resourceProvider: ResourceProvider,
    @Assisted private val navigateToImage: (Image) -> Unit
): ViewModel() {
    private val _state: MutableState<ImageListState> = mutableStateOf(
        ImageListState.ImageListStateLoading
    )
    val state: State<ImageListState> = _state


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
            onErrorActionClick,
            ::onImageClick
        )
    }

    private fun onImageClick(image: Image) {
        navigateToImage(image)
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted navigateToImage: (Image) -> Unit
        ): ImageListViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST", "unused")
        fun provideFactory(
            assistedFactory: Factory, // this is the Factory interface
            // declared above
            navigateToImage: (Image) -> Unit
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(navigateToImage) as T
            }
        }
    }
}

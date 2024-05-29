package com.resy.picsum.android.ui.image

import androidx.compose.runtime.State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.resy.picsum.data.model.Image
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ImageViewModel.Factory::class)
class ImageViewModel @AssistedInject constructor(
    @Assisted private val image: Image
): ViewModel() {
    private val _state: MutableState<ImageState> = mutableStateOf(
        ImageState.ImageStateSuccess(
            orientation = Orientation.UNKNOWN,
            image = image
        )
    )
    val state: State<ImageState> = _state

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted image: Image
        ): ImageViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST", "unused")
        fun provideFactory(
            assistedFactory: Factory, // this is the Factory interface
            // declared above
            image: Image
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(image) as T
            }
        }
    }
}

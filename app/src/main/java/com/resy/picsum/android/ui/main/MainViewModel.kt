package com.resy.picsum.android.ui.main

import android.net.http.HttpException
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Thread.sleep
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
    private val getImageListUseCase: GetImageListUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(
        MainState.MainStateLoading
    )
    val state: StateFlow<MainState> = _state


    /**
     * To be called when the view is launched.
     */
    init {
        loadImages()
    }

    private fun loadImages() {
        setLoadImageStartState()
        viewModelScope.launch {
            if(BuildConfig.DEBUG) {
                withContext(Dispatchers.IO) {
                    sleep(1000)
                }
            }
            try {
                getImageListUseCase().collect {result ->
                    updateOnImageState(result)
                }
            } catch(t: Throwable) {
                setOnErrorState()
            }
        }
    }

    private fun setLoadImageStartState() {
        if(_state.value is MainState.MainStateSuccess) {
            updateSuccessState()
        } else {
            updateLoadingState()
        }
    }

    private fun setOnErrorState() {
        val stateValue = _state.value
        if(stateValue is MainState.MainStateSuccess) {
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
        images: List<Image> = (_state.value as? MainState.MainStateSuccess)?.images?: listOf(),
        errorMessage: Event<String>? = (_state.value as? MainState.MainStateSuccess)?.errorMessage,
        onErrorActionClick: () -> Unit = (_state.value as? MainState.MainStateSuccess)?.onErrorActionClick ?: ::loadImages
    ) {
        _state.value = MainState.MainStateSuccess(
            images,
            errorMessage,
            onErrorActionClick
        )
    }

    private fun updateErrorState(
        onErrorActionClick: () -> Unit = (_state.value as? MainState.MainStateError)?.onErrorActionClick ?: ::loadImages
    ) {
        _state.value = MainState.MainStateError(
            onErrorActionClick = onErrorActionClick
        )
    }

    private fun updateLoadingState() {
        _state.value = MainState.MainStateLoading
    }
}

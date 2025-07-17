package com.gauravbajaj.reelly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gauravbajaj.reelly.data.model.Subtitle
import com.gauravbajaj.reelly.data.repository.SubtitlesRepository
import com.gauravbajaj.reelly.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * The ViewModel for the [VideoGalleryPickerScreen].
 *
 * This ViewModel is responsible for managing the application's business logic for the home screen.
 * It provides a [StateFlow] of [UIState] that the UI can observe to update based on the current state.
 *
 * The [loadSubtitles] function is used to trigger the loading of the subtitles. It sets the [UIState] to
 * [UIState.Loading] and then calls the [SubtitlesRepository.getSubtitles] function to load the data.
 * When the data is loaded, it sets the [UIState] to [UIState.Success] with the loaded data. If an
 * exception occurs while loading the data, it sets the [UIState] to [UIState.Error] with the error
 * message.
 */
@HiltViewModel
class VideoGalleryPickerViewModel @Inject constructor(
    private val subtitlesRepository: SubtitlesRepository
) : ViewModel() {

    private val _uiSubtitlesState = MutableStateFlow<UIState<List<Subtitle>>>(UIState.Initial)
    val uiSubtitlesState: StateFlow<UIState<List<Subtitle>>> = _uiSubtitlesState

    fun loadSubtitles() {
        viewModelScope.launch {
            _uiSubtitlesState.value = UIState.Loading
            try {
                subtitlesRepository.getSubtitles()
                    .collect { subtitles ->
                        _uiSubtitlesState.value = UIState.Success(subtitles)
                    }
            } catch (e: Exception) {
                _uiSubtitlesState.value = UIState.Error(e.message ?: "Failed to load subtitles")
            }
        }
    }
}

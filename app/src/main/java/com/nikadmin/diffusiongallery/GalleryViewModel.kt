package com.nikadmin.diffusiongallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI

data class GalleryState(
    val images: List<Image> = listOf(),
    val prompts: List<Prompt> = listOf(),
    val status: Status = Ready,
)

sealed class Status {}
object Ready : Status()
object Loading : Status()
class Error(val message: String) : Status()

class GalleryViewModel : ViewModel() {
    private val _state = MutableStateFlow(GalleryState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        Log.d("ViewModel", "Load called")
        _state.update { state -> state.copy(status = Loading) }

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModel", "Before load")

            try {
                Log.d("ViewModel", "Before request")

                val imageResponse = Gallery.api.getImages()
                val promptResponse = Gallery.api.getPrompts()

                Log.d("ViewModel", "After request")

                val images = imageResponse.data.images

                images.forEach { image ->
                    image.src = Gallery.resolve(image.src)
                }

                val prompts = promptResponse.data.prompts

                Log.d("ViewModel", "Data prepared")
                withContext(Dispatchers.Main) {
                    _state.update { GalleryState(images, prompts) }
                    Log.d("ViewModel", "Set state")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.update { state -> state.copy(status = Error(e.toString())) }
                }
            }

            Log.d("ViewModel", "After load")
        }
    }
}
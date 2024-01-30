package com.nikadmin.diffusiongallery

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.net.URI

data class Image(
    val name: String,
    var src: String,
    val url: String,
)

data class ImageDetails(
    val name: String,
    val description: String,
    val src: String,
    val prompt: String,
    val created_at: String,
)

data class Prompt(
    val name: String,
    val text: String,
    val url: String,
)

data class PromptDetail(
    val name: String,
    val text: String,
    val model: String,
    val width: Int,
    val height: Int,
    val steps: Int,
    val seed: String,
    val images: String,
    val generate: String,
)

data class ApiResponse<T>(
    val status: String,
    val data: T
)

data class ImageListData(
    val images: List<Image>
)

data class ImageDetailsData(
    val image: ImageDetails
)

data class PromptListData(
    val prompts: List<Prompt>
)

data class PromptDetailsData(
    val prompt: PromptDetail
)


interface GalleryApi {
    @GET("/api/images/")
    suspend fun getImages(): ApiResponse<ImageListData>

    @GET("{image_url}")
    suspend fun getImage(
        @Path(value = "image_url", encoded = true) imageId: String
    ): ApiResponse<ImageDetailsData>

    @GET("/api/prompts/")
    suspend fun getPrompts(): ApiResponse<PromptListData>

    @GET("{prompt_url}")
    suspend fun getPrompt(
        @Path(value = "prompt_url", encoded = true) promptId: String
    ): ApiResponse<PromptDetailsData>
}

object Gallery {
    const val baseUrl = "http://192.168.0.100:8000/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: GalleryApi = retrofit.create(GalleryApi::class.java)

    fun resolve(url: String): String {
        return URI(baseUrl).resolve(url).toString()
    }
}
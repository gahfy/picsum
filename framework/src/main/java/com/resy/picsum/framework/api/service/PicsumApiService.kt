package com.resy.picsum.framework.api.service

import com.resy.picsum.framework.api.model.ApiImage
import retrofit2.http.GET

/**
 * The service to retrieve data from the API.
 */
interface PicsumApiService {
    /**
     * Returns the list of images from the API.
     *
     * @return the list of images from the API
     */
    @GET("list")
    suspend fun getImageList(): List<ApiImage>
}

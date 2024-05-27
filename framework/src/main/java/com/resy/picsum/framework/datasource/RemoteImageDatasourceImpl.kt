package com.resy.picsum.framework.datasource

import com.resy.picsum.data.datasource.RemoteImageDatasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.framework.api.service.PicsumApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Remote datasource for the images which is based on the API.
 *
 * @property apiService the API service to use to retrieve the images
 *
 * @constructor Instantiates a new [RemoteImageDatasourceImpl].
 *
 * @param apiService the API service to use to retrieve the images to set
 */
class RemoteImageDatasourceImpl(
    private val apiService: PicsumApiService
): RemoteImageDatasource {

    /**
     * Returns the list of images from the API.
     *
     * @return the list of images from the API
     */
    override suspend fun getRemoteImageList(): List<Image> {
        val images = withContext(Dispatchers.IO) {
            apiService.getImageList()
        }
        return withContext(Dispatchers.Default) {
            images.map {
                it.toImage()
            }
        }
    }
}

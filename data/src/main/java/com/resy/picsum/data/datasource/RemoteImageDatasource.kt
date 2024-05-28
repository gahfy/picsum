package com.resy.picsum.data.datasource

import com.resy.picsum.data.model.Image

/**
 * The remote datasource contract for Image.
 */
interface RemoteImageDatasource {
    /**
     * Returns the list of images retrieved from the remote datasource.
     *
     * @return the list of images retrieved from the remote datasource
     */
    suspend fun getRemoteImageList(): List<Image>
}

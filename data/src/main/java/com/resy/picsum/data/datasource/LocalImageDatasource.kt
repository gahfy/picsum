package com.resy.picsum.data.datasource

import com.resy.picsum.data.model.Image

/**
 * The local datasource contract for Image
 */
interface LocalImageDatasource {
    /**
     * Returns the list of images retrieved from the local datasource.
     *
     * @return the list of images retrieved from the local datasource.
     */
    suspend fun getLocalImageList(): List<Image>

    /**
     * Inserts the given list of images in the local datasource.
     *
     * @param images the list of images to be inserted in the local datasource
     */
    suspend fun insert(images: List<Image>)

    /**
     * Updates the given list of images in the local datasource.
     *
     * @param images the list of images to be updated in the local datasource
     */
    suspend fun update(images: List<Image>)

    /**
     * Deletes the given list of images in the local datasource.
     *
     * @param images the list of images to be deleted in the local datasource
     */
    suspend fun delete(images: List<Image>)
}

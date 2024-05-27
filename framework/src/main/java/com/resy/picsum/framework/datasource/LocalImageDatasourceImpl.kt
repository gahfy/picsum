package com.resy.picsum.framework.datasource

import com.resy.picsum.data.datasource.LocalImageDatasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.framework.database.dao.ImageDao
import com.resy.picsum.framework.database.entity.ImageDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Local datasource for the images which is based on the local database.
 *
 * @property imageDao the Database access object for images to get the data from the local database
 *
 * @constructor Instantiates a new [LocalImageDatasourceImpl].
 *
 * @param imageDao the Database access object for images to get the data from the local database to
 *                 set
 */
class LocalImageDatasourceImpl(
    private val imageDao: ImageDao
): LocalImageDatasource {

    /**
     * Returns the list of images retrieved from the database.
     *
     * @return the list of images retrieved from the database
     */
    override suspend fun getLocalImageList() : List<Image> {
        val dbImages = withContext(Dispatchers.IO) {
            imageDao.getImages()
        }
        return withContext(Dispatchers.Default) {
            dbImages.map { it.toImage() }
        }
    }

    /**
     * Inserts the given list of images into the database.
     *
     * @param images the list of images to insert into the database
     */
    override suspend fun insert(images: List<Image>) {
        val dbImages = withContext(Dispatchers.Default) {
            images.map { ImageDbEntity(it) }
        }
        withContext(Dispatchers.IO) {
            imageDao.insertAll(dbImages)
        }
    }

    /**
     * Updates the given list of images in the database.
     *
     * @param images the list of images to update in the database.
     */
    override suspend fun update(images: List<Image>) {
        val dbImages = withContext(Dispatchers.Default) {
            images.map { ImageDbEntity(it) }
        }
        withContext(Dispatchers.IO) {
            imageDao.updateAll(dbImages)
        }
    }

    /**
     * Deletes the given list of images from the database.
     *
     * @param images the list of images to delete from the database
     */
    override suspend fun delete(images: List<Image>) {
        val dbImages = withContext(Dispatchers.Default) {
            images.map { ImageDbEntity(it) }
        }
        withContext(Dispatchers.IO) {
            imageDao.deleteAll(dbImages)
        }
    }
}

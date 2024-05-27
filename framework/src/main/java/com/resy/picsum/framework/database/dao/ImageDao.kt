package com.resy.picsum.framework.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.resy.picsum.framework.database.entity.ImageDbEntity

/**
 * Database Access Object for Images
 */
@Dao
interface ImageDao {
    /**
     * Returns the list of images from the database.
     *
     * @return the list of images from the database
     */
    @Query("SELECT * FROM imagedbentity")
    suspend fun getImages(): List<ImageDbEntity>

    /**
     * Inserts the given list of images into the database.
     *
     * @param images the list of images to insert into the database
     */
    @Insert
    suspend fun insertAll(images: List<ImageDbEntity>)

    /**
     * Updates the given list of images in the database.
     *
     * @param images the list of images to update in the database
     */
    @Update
    suspend fun updateAll(images: List<ImageDbEntity>)

    /**
     * Removes the given list of images from the database.
     *
     * @param images the list of images to remove from the database
     */
    @Delete
    suspend fun deleteAll(images: List<ImageDbEntity>)
}

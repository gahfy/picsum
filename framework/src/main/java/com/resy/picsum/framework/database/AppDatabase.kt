package com.resy.picsum.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resy.picsum.framework.database.dao.CachedFileDao
import com.resy.picsum.framework.database.dao.ImageDao
import com.resy.picsum.framework.database.entity.CachedFileDbEntity
import com.resy.picsum.framework.database.entity.ImageDbEntity

/**
 * The database to be used in the application.
 */
@Database(
    entities = [
        ImageDbEntity::class,
        CachedFileDbEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    /**
     * Returns an object to access the data of the images.
     *
     * @return an object to access the data of the images
     */
    abstract fun imageDao(): ImageDao

    /**
     * Returns an object to access the data of the cached files.
     *
     * @return an object to access the data of the cached files
     */
    abstract fun cachedFileDao(): CachedFileDao
}

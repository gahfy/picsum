package com.resy.picsum.framework.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.resy.picsum.framework.database.entity.CachedFileDbEntity

/**
 * Database access object for accessing the cached files.
 */
@Dao
interface CachedFileDao {
    /**
     * Returns the cached file stored in database for the given url, or null if no one exists
     *
     * @return the cached file stored in database for the given url, or null if no one exists
     */
    @Query("SELECT * FROM CachedFileDbEntity WHERE fileUrl=:fileUrl")
    suspend fun getCachedFile(fileUrl: String): CachedFileDbEntity?

    /**
     * Inserts the given cached file in database.
     *
     * @param cachedFile the cached file to insert in database.
     */
    @Insert
    suspend fun insertCachedFile(cachedFile: CachedFileDbEntity)

    /**
     * Updates the given cached file in database.
     *
     * @param cachedFile the cached file to update in database
     */
    @Update
    suspend fun updateCachedFile(cachedFile: CachedFileDbEntity)

    /**
     * Returns the list of cached file which are expired
     *
     * @param currentTimestamp the current timestamp
     *
     * @return the list of cached file which are expired
     */
    @Query("SELECT * FROM CachedFileDbEntity WHERE expirationTimestamp<:currentTimestamp")
    suspend fun getCachedFilesToClear(currentTimestamp: Long): List<CachedFileDbEntity>

    /**
     * Removes the given list of cached files from the database
     *
     * @param cachedFiles the list of cached files to remove from the database
     */
    @Delete
    suspend fun delete(cachedFiles: List<CachedFileDbEntity>)
}

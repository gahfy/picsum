package com.resy.picsum.framework.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.resy.picsum.data.model.CachedFile

/**
 * Model which represents a cached file as it is stored in database.
 *
 * @property fileUrl             the url of the file in the cache
 * @property expirationTimestamp the timestamp when the cache expires
 * @property filename            the name of the file in the cache directory
 *
 * @constructor Instantiates a new [CachedFileDbEntity].
 *
 * @param fileUrl             the url of the file in the cache to set
 * @param expirationTimestamp the timestamp when the cache expires to set
 * @param filename            the name of the file in the cache directory to set
 */
@Entity
data class CachedFileDbEntity(
    @PrimaryKey
    val fileUrl: String,
    val expirationTimestamp: Long,
    val filename: String
) {
    /**
     * Instantiates a new [CachedFileDbEntity] with the same data as the ones in the given cached
     * file.
     *
     * @param cachedFile the cached file from which to get the data
     */
    constructor(cachedFile: CachedFile) : this(
        fileUrl = cachedFile.fileUrl,
        expirationTimestamp = cachedFile.expirationTimestamp,
        filename = cachedFile.filename
    )

    /**
     * Returns a new instance of [CachedFile] with the same data as the ones in the current
     * instance.
     *
     * @return a new instance of [CachedFile] with the same data.
     */
    fun toCachedFile(): CachedFile =
        CachedFile(
            fileUrl = fileUrl,
            expirationTimestamp = expirationTimestamp,
            filename = filename
        )
}

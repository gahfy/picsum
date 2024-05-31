package com.resy.picsum.data.model

/**
 * Model for a cached file in the application.
 *
 * @property fileUrl             the url of the file in the cache
 * @property expirationTimestamp the timestamp when the file in the cache expires
 * @property filename            the name of the file in the cache directory
 *
 * @constructor Instantiates a new [CachedFile]
 *
 * @param fileUrl             the url of the file in the cache to set
 * @param expirationTimestamp the timestamp when the file in the cache expires to set
 * @param filename            the name of the file in the cache directory to set
 */
data class CachedFile(
    val fileUrl: String,
    val expirationTimestamp: Long,
    val filename: String
)

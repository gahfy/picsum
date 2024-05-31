package com.resy.picsum.data.repository

import com.resy.picsum.data.datasource.CachedFileDatasource
import com.resy.picsum.data.model.CachedFile

/**
 * The contract interface for the repository for cached file
 */
interface CachedFileRepository {
    /**
     * Returns the cached file for a given url.
     *
     * @param url  the url for which to return the cached file
     * @param type the type of file (extension) which is expected
     *
     * @return the cached file for a given url
     */
    suspend fun getCachedFile(url: String, type: String): CachedFile
}

/**
 * Repository for cached file which is based on [CachedFileDatasource].
 *
 * @property cachedFileDatasource the datasource to use to get the cached files
 *
 * @constructor Instantiates a new [CachedFileRepositoryImpl].
 *
 * @param cachedFileDatasource the datasource to use to get the cached files to set
 */
class CachedFileRepositoryImpl(
    private val cachedFileDatasource: CachedFileDatasource
): CachedFileRepository {

    /**
     * Returns the cached file for a given url from the datasource.
     *
     * @param url  the url for which to return the cached file
     * @param type the type of file (extension) which is expected
     *
     * @return the cached file for a given url from the datasource
     */
    override suspend fun getCachedFile(url: String, type: String): CachedFile =
        cachedFileDatasource.getCachedFile(url, type)

}

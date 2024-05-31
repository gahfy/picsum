package com.resy.picsum.data.datasource

import com.resy.picsum.data.model.CachedFile

/**
 * The datasource contract for cached files
 */
interface CachedFileDatasource {
    /**
     * Returns the cached file for a given url.
     *
     * @param urlString the url for which to get the cached file
     * @param type      the type of file expected (extension of the file)
     *
     * @return the cached file for the given url
     */
    suspend fun getCachedFile(urlString: String, type: String): CachedFile
}

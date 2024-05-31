package com.resy.picsum.framework.datasource

import android.content.Context
import android.net.TrafficStats
import com.resy.picsum.data.datasource.CachedFileDatasource
import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.framework.database.dao.CachedFileDao
import com.resy.picsum.framework.database.entity.CachedFileDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/** The size of the buffer */
private const val BUFFER_SIZE = 8192

/** The length of the file names in the cache */
private const val CACHE_FILENAME_LENGTH = 10

/**
 * Implementation of the cached file datasource, to provide cached file
 *
 * @property cachedFileDao the database access object for cached files
 * @property context       the Context in which the application is running
 *
 * @constructor Instantiates a new [CachedFileDatasourceImpl].
 *
 * @param cachedFileDao the database access object for cached files to set
 * @param context       the Context in which the application is running to set
 */
class CachedFileDatasourceImpl(
    private val cachedFileDao: CachedFileDao,
    private val context: Context
) : CachedFileDatasource {

    /**
     * Returns the cached file for a given url.
     *
     * @param urlString the url for which to get the cached file
     * @param type      the type of file expected (extension of the file)
     *
     * @return the cached file for the given url
     */
    override suspend fun getCachedFile(urlString: String, type: String): CachedFile {
        var maxAge = 0
        var filename = ""
        clearCache()

        // Check if a valid entry exists in database
        val cachedFile = withContext(Dispatchers.IO) { cachedFileDao.getCachedFile(urlString) }
        if(cachedFile != null) {
            val file = File(context.cacheDir, cachedFile.filename)
            if (withContext(Dispatchers.IO) { file.exists() }) {
                return cachedFile.toCachedFile()
            } else {
                withContext(Dispatchers.IO) {
                    cachedFileDao.delete(listOf(cachedFile))
                }
            }
        }

        return withContext(Dispatchers.IO) {
            TrafficStats.setThreadStatsTag(cachedFile?.fileUrl.hashCode())

            val url = URL(urlString)
            val connection = url.openConnection()
            connection.connect()

            val cacheControl = connection.getHeaderField("cache-control")

            if(cacheControl == null) {
                maxAge = 0
            } else {
                val directives = cacheControl.split(",").map { it.trim() }
                for (directive in directives) {
                    if (directive.startsWith("max-age", ignoreCase = true)) {
                        val parts = directive.split("=")
                        if (parts.size == 2) {
                            maxAge = parts[1].toIntOrNull()?:0
                        }
                    }
                }
            }

            val inputStream = url.openStream()

            // Filename will 10 random characters followed by the extension
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            filename = "${(1..CACHE_FILENAME_LENGTH).map { allowedChars.random() }.joinToString("")}.${type}"

            val file = File(context.cacheDir, filename)
            val outputStream = FileOutputStream(file)

            val data = ByteArray(BUFFER_SIZE)

            var count = inputStream.read(data)
            while(count != -1) {
                outputStream.write(data, 0, count)
                count = inputStream.read(data)
            }


            inputStream.close()
            outputStream.close()

            val result = CachedFile(
                urlString,
                System.currentTimeMillis() + maxAge.toLong(),
                filename
            )
            withContext(Dispatchers.IO) {
                cachedFileDao.insertCachedFile(CachedFileDbEntity(result))
            }
            result
        }
    }

    /**
     * Clears the outdated files and entries in database from the cache
     */
    private suspend fun clearCache() = withContext(Dispatchers.IO) {
        val cachedFilesToRemove = cachedFileDao.getCachedFilesToClear(System.currentTimeMillis())
        cachedFilesToRemove.forEach { cachedFile ->
            val file = File(context.cacheDir, cachedFile.filename)
            if(file.exists()) {
                file.delete()
            }
        }
        cachedFileDao.delete(cachedFilesToRemove)
    }
}

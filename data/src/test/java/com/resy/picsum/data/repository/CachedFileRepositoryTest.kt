package com.resy.picsum.data.repository

import com.resy.picsum.data.datasource.CachedFileDatasource
import com.resy.picsum.data.model.CachedFile
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CachedFileRepositoryTest {
    @Test
    fun testGetCachedFile() = runTest {
        val datasource = object: CachedFileDatasource {
            override suspend fun getCachedFile(urlString: String, type: String) =
                CachedFile(urlString, 10L, "abc")
        }

        val repository = CachedFileRepositoryImpl(datasource)

        val cachedFile = repository.getCachedFile("Some url", "jpeg")

        assertEquals(
            "Second result should be the one from datasource",
            CachedFile("Some url", 10L, "abc"),
            cachedFile
        )
    }
}

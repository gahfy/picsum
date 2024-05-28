package com.resy.picsum.framework.datasource

import com.resy.picsum.data.model.Image
import com.resy.picsum.framework.api.model.ApiImage
import com.resy.picsum.framework.api.service.PicsumApiService
import com.resy.picsum.framework.database.entity.ImageDbEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RemoteImageDatasourceImplTest {
    val remoteImages = mutableListOf<ApiImage>()

    @Before
    fun setup() {
        remoteImages.clear()
    }

    @Test
    fun testGetRemoteImageList() = runTest {
        val apiImage0 = ApiImage(
            0,
            "jpeg",
            5000,
            3333,
            "0.jpeg",
            "Alejandro Escamilla",
            "http://authorSite",
            "http://postSite"
        )
        val apiImage1 = ApiImage(
            1,
            "png",
            4000,
            2000,
            "1.png",
            "John Doe",
            "http://authorSite1",
            "http://postSite1"
        )
        val image0 = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 4000, 2000, "1.png", "John Doe")

        remoteImages.addAll(listOf(apiImage0, apiImage1))
        val remoteImageDatasourceImpl = RemoteImageDatasourceImpl(api)

        val retrievedImages = remoteImageDatasourceImpl.getRemoteImageList()

        assertEquals("List should have 2 elements", 2, retrievedImages.size)
        assertTrue("List should contain image0", retrievedImages.contains(image0))
        assertTrue("List should contain image1", retrievedImages.contains(image1))

    }

    val api = object: PicsumApiService {
        override suspend fun getImageList(): List<ApiImage> {
            return remoteImages
        }
    }
}

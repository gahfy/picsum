package com.resy.picsum.data.repository

import com.resy.picsum.data.datasource.LocalImageDatasource
import com.resy.picsum.data.datasource.RemoteImageDatasource
import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ImageRepositoryTest {
    val localImages = mutableListOf<Image>()
    val remoteImages = mutableListOf<Image>()

    val localInsertCalls = mutableListOf<List<Image>>()
    val localUpdateCalls = mutableListOf<List<Image>>()
    val localDeleteCalls = mutableListOf<List<Image>>()

    private val localImageDatasource = object: LocalImageDatasource {
        override suspend fun getLocalImageList(): List<Image> =
            localImages

        override suspend fun insert(images: List<Image>) {
            localInsertCalls.add(images)
            localImages.addAll(images)
        }

        override suspend fun update(images: List<Image>) {
            localUpdateCalls.add(images)
            images.forEach { currentImage ->
                localImages.removeIf { it.id == currentImage.id }
            }
            localImages.addAll(images)
        }

        override suspend fun delete(images: List<Image>) {
            localDeleteCalls.add(images)
            images.forEach { currentImage ->
                localImages.removeIf {
                    it.id == currentImage.id
                }
            }
        }
    }

    private val remoteImageDatasource = object: RemoteImageDatasource {
        override suspend fun getRemoteImageList(): List<Image> =
            remoteImages
    }

    @Before
    fun setup() {
        localImages.clear()
        remoteImages.clear()
        localInsertCalls.clear()
        localUpdateCalls.clear()
        localDeleteCalls.clear()
    }

    @Test
    fun testGetImages() = runTest {
        val image0 = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 5000, 3333, "1.jpeg", "Alejandro Escamilla")
        val image2 = Image(2, 5000, 3333, "2.jpeg", "Alejandro Escamilla")
        val image2Updated = Image(2, 4000, 3333, "2.jpeg", "Alejandro Escamilla")
        val image3 = Image(3, 5000, 3333, "3.jpeg", "Alejandro Escamilla")
        localImages.add(image0)
        localImages.add(image1)
        localImages.add(image2)

        remoteImages.add(image1)
        remoteImages.add(image2Updated)
        remoteImages.add(image3)

        val repository = ImageRepositoryImpl(localImageDatasource, remoteImageDatasource)

        val images = repository.getImages()

        val imagesEmitted = images.first()
        assertEquals("First from local should have 3 items", 3, imagesEmitted.result.size)
        assertEquals("First result should come from local datasource", Datasource.LOCAL, imagesEmitted.datasource)
        assertTrue("First from local should have the image 0", imagesEmitted.result.contains(image0))
        assertTrue("First from local should have the image 1", imagesEmitted.result.contains(image1))
        assertTrue("First from local should have the image 2", imagesEmitted.result.contains(image2))

        val imagesEmitted2 = images.drop(1).first()
        assertEquals("Second after remote should have 4 items", 3, imagesEmitted2.result.size)
        assertEquals("Second result should come from remote datasource", Datasource.REMOTE, imagesEmitted2.datasource)
        assertTrue("Second from remote should have the image 1", imagesEmitted2.result.contains(image1))
        assertTrue("Second from remote should have the image 2 updated", imagesEmitted2.result.contains(image2Updated))
        assertTrue("Second from remote should have the image 3", imagesEmitted2.result.contains(image3))
        assertEquals("Insert should have been called only once", 1, localInsertCalls.size)
        assertEquals("Only one image should have been inserted", 1, localInsertCalls[0].size)
        assertEquals("Only image 3 should have been inserted", image3, localInsertCalls[0][0])
        assertEquals("Update should have been called only once", 1, localUpdateCalls.size)
        assertEquals("Only one image should have been updated", 1, localUpdateCalls[0].size)
        assertEquals("Only image 2 should have been updated", image2Updated, localUpdateCalls[0][0])
        assertEquals("Delete should have been called only once", 1, localDeleteCalls.size)
        assertEquals("Only one image should have been deleted", 1, localDeleteCalls[0].size)
        assertEquals("Only image 0 should have been deleted", image0, localDeleteCalls[0][0])
    }
}

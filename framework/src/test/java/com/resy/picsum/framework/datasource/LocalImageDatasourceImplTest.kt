package com.resy.picsum.framework.datasource

import com.resy.picsum.data.model.Image
import com.resy.picsum.framework.database.dao.ImageDao
import com.resy.picsum.framework.database.entity.ImageDbEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LocalImageDatasourceImplTest {
    private val imageEntities: MutableList<ImageDbEntity> = mutableListOf<ImageDbEntity>()

    @Before
    fun setup() {
        imageEntities.clear()
    }

    @Test
    fun testGetLocalImageList() = runTest {
        val imageEntity0 = ImageDbEntity(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val imageEntity1 = ImageDbEntity(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")
        val image0 = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")

        imageEntities.add(imageEntity0)
        imageEntities.add(imageEntity1)

        val localImageDatasourceImpl = LocalImageDatasourceImpl(dao)
        val localImages = localImageDatasourceImpl.getLocalImageList()

        assertEquals("List should have 2 items only", 2, localImages.size)
        assertTrue("List should contain image0", localImages.contains(image0))
        assertTrue("List should contain image1", localImages.contains(image1))
    }

    @Test
    fun testInsertLocalImageList() = runTest {
        val imageEntity0 = ImageDbEntity(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val imageEntity1 = ImageDbEntity(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")
        val image0 = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")

        val localImageDatasourceImpl = LocalImageDatasourceImpl(dao)
        localImageDatasourceImpl.insert(listOf(image0, image1))

        assertEquals("List should have 2 items only", 2, imageEntities.size)
        assertTrue("List should contain image0", imageEntities.contains(imageEntity0))
        assertTrue("List should contain image1", imageEntities.contains(imageEntity1))
    }

    @Test
    fun testUpdate() = runTest {
        val imageEntity0 = ImageDbEntity(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val imageEntity1 = ImageDbEntity(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")

        imageEntities.addAll(listOf(imageEntity0, imageEntity1))

        val localImageDatasourceImpl = LocalImageDatasourceImpl(dao)
        localImageDatasourceImpl.delete(listOf(image1))

        assertEquals("List should have 1 item only", 1, imageEntities.size)
        assertTrue("List should contain image0", imageEntities.contains(imageEntity0))
        assertFalse("List should not contain image1", imageEntities.contains(imageEntity1))
    }

    @Test
    fun testDelete() = runTest {
        val imageEntity0 = ImageDbEntity(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val imageEntity1 = ImageDbEntity(1, 4000, 2000, "1.jpeg", "Alejandro Escamilla")
        val image0 = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image1 = Image(1, 3000, 3000, "2.jpeg", "John Doe")

        val imageEntity1Updated = ImageDbEntity(1, 3000, 3000, "2.jpeg", "John Doe")

        imageEntities.addAll(listOf(imageEntity0, imageEntity1))

        val localImageDatasourceImpl = LocalImageDatasourceImpl(dao)
        localImageDatasourceImpl.update(listOf(image1, image0))

        assertEquals("List should have 2 items only", 2, imageEntities.size)
        assertTrue("List should contain image0", imageEntities.contains(imageEntity0))
        assertTrue(
            "List should contain image1 updated",
            imageEntities.contains(imageEntity1Updated)
        )
    }

    private val dao = object : ImageDao {
        override suspend fun getImages(): List<ImageDbEntity> = imageEntities

        override suspend fun insertAll(images: List<ImageDbEntity>) {
            imageEntities.addAll(images)
        }

        override suspend fun updateAll(images: List<ImageDbEntity>) {
            images.forEach { currentImage ->
                imageEntities.removeIf { it.id == currentImage.id }
            }
            imageEntities.addAll(images)
        }

        override suspend fun deleteAll(images: List<ImageDbEntity>) {
            imageEntities.removeAll(images)
        }
    }
}

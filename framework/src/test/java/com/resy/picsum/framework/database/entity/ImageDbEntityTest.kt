package com.resy.picsum.framework.database.entity

import com.resy.picsum.data.model.Image
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageDbEntityTest {
    @Test
    fun testImageDbEntityFromImage() {
        val image = Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")

        val imageDbEntity = ImageDbEntity(image)

        assertEquals("Identifiers should be equal", image.id, imageDbEntity.id)
        assertEquals("Widths should be equal", image.width, imageDbEntity.width)
        assertEquals("Heights should be equal", image.height, imageDbEntity.height)
        assertEquals("Filenames should be equal", image.filename, imageDbEntity.filename)
        assertEquals("Authors should be equal", image.author, imageDbEntity.author)
    }

    @Test
    fun testImageDbEntityToImage() {
        val imageDbEntity = ImageDbEntity(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla")
        val image = imageDbEntity.toImage()

        assertEquals("Identifiers should be equal", imageDbEntity.id, image.id)
        assertEquals("Widths should be equal", imageDbEntity.width, image.width)
        assertEquals("Heights should be equal", imageDbEntity.height, image.height)
        assertEquals("Filenames should be equal", imageDbEntity.filename, image.filename)
        assertEquals("Authors should be equal", imageDbEntity.author, image.author)
    }
}

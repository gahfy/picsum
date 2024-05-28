package com.resy.picsum.framework.api.model

import junit.framework.TestCase
import org.junit.Test

class ApiImageTest {
    @Test
    fun testApiImageToImage() {
        val apiImage = ApiImage(
            id = 99L,
            format = "jpeg",
            width = 5000,
            height = 3333,
            filename = "0.jpeg",
            author = "Alejandro Escamilla",
            authorUrl = "https://unsplash.com/photos/yC-Yzbqy7PY",
            postUrl = "https://unsplash.com/photos/yC-Yzbqy7PY"
        )

        val image = apiImage.toImage()

        TestCase.assertEquals(
            "Identifier of image should be equal to the one of ApiImage",
            99,
            image.id
        )
        TestCase.assertEquals(
            "Width of image should be equal to the one of ApiImage",
            5000,
            image.width
        )
        TestCase.assertEquals(
            "Height of image should be equal to the one of ApiImage",
            3333,
            image.height
        )
        TestCase.assertEquals(
            "Filename of image should be equal to the one of ApiImage",
            "0.jpeg",
            image.filename
        )
        TestCase.assertEquals(
            "Author of image should be equal to the one of ApiImage",
            "Alejandro Escamilla",
            image.author
        )
    }
}

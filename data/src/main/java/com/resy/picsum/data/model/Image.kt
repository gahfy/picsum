package com.resy.picsum.data.model

/**
 * Model which represents an Image as it is used in the application.
 *
 * @property id       the unique identifier of the image
 * @property width    the width (in pixels) of the image
 * @property height   the height (in pixels) of the image
 * @property filename the name of the image file
 * @property author   the name of the author of the image
 *
 * @constructor Instantiates a new [Image].
 *
 * @param id       the unique identifier of the image to set
 * @param width    the width (in pixels) of the image to set
 * @param height   the height (in pixels) of the image to set
 * @param filename the name of the image file to set
 * @param author   the name of the author of the image to set
 */
data class Image(
    val id: Long,
    val width: Int,
    val height: Int,
    val filename: String,
    val author: String
)

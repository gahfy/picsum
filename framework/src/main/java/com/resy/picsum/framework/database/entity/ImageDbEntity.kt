package com.resy.picsum.framework.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.resy.picsum.data.model.Image

/**
 * Model which represents an image as it is stored in the database.
 *
 * @property id       the unique identifier of the image
 * @property width    the width (in pixels) of the image
 * @property height   the height (in pixels) of the image
 * @property filename the name of the image file
 * @property author   the name of the author of the image
 * @property type     the type of file (png, jpg, ...) of the image
 *
 * @constructor Instantiates a new [ImageDbEntity].
 *
 * @param id       the unique identifier of the image to set
 * @param width    the width (in pixels) of the image to set
 * @param height   the height (in pixels) of the image to set
 * @param filename the name of the image file to set
 * @param author   the name of the author of the image to set
 * @param type     the type of file (png, jpg, ...) of the image to set
 */
@Entity
data class ImageDbEntity(
    @PrimaryKey
    val id: Long,
    val width: Int,
    val height: Int,
    val filename: String,
    val author: String,
    val type: String
) {
    /**
     * Instantiates a new [ImageDbEntity] with data from the given [Image].
     *
     * @param image the image from which to get the data to populate in this new instance.
     */
    constructor(image: Image): this(
        id = image.id,
        width = image.width,
        height = image.height,
        filename = image.filename,
        author = image.author,
        type = image.type
    )

    /**
     * Returns a new instance of [Image] with the same data as the given [ImageDbEntity].
     *
     * @return a new instance of [Image] with the same data.
     */
    fun toImage(): Image = Image(
        id = id,
        width = width,
        height = height,
        filename = filename,
        author = author,
        type = type
    )
}

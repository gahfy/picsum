package com.resy.picsum.data.model

import java.io.Serializable

/**
 * Model which represents an Image as it is used in the application.
 *
 * @property id       the unique identifier of the image
 * @property width    the width (in pixels) of the image
 * @property height   the height (in pixels) of the image
 * @property filename the name of the image file
 * @property author   the name of the author of the image
 * @property type     the type of the file of the image (jpg, png, ...)
 *
 * @constructor Instantiates a new [Image].
 *
 * @param id       the unique identifier of the image to set
 * @param width    the width (in pixels) of the image to set
 * @param height   the height (in pixels) of the image to set
 * @param filename the name of the image file to set
 * @param author   the name of the author of the image to set
 * @param type     the type of the file of the image (jpg, png, ...) to set
 */
@Suppress("SerialVersionUIDInSerializableClass")
data class Image(
    val id: Long,
    val width: Int,
    val height: Int,
    val filename: String,
    val author: String,
    val type: String
): Serializable {
    override fun toString(): String {
        return "$id\n$width\n$height\n$filename\n$author\n$type"
    }
}

@Suppress("MagicNumber")
fun String.toImage(): Image {
    val splitted = split("\n")
    if(splitted.size == 6) {
        return Image(
            splitted[0].toLong(),
            splitted[1].toInt(),
            splitted[2].toInt(),
            splitted[3],
            splitted[4],
            splitted[5]
        )
    } else {
        require(false) {"String is not well formed"}
        // We don't care as previous statement throws an exception
        return Image(0, 0, 0, "", "", "")
    }
}

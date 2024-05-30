package com.resy.picsum.framework.api.model

import com.resy.picsum.data.model.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model which represents an image as it is described in the picsum api.
 *
 * @property id        The unique identifier of the image
 * @property format    The file format of the image
 * @property width     The width (in pixels) of the image
 * @property height    The height (in pixels) of the image
 * @property filename  The name of the image file
 * @property author    The name of the author of the image
 * @property authorUrl The url for the author of the image
 * @property postUrl   The url for the post of the image
 *
 * @constructor Instantiates a new [ApiImage].
 *
 * @param id        The unique identifier of the image to set
 * @param format    The file format of the image to set
 * @param width     The width (in pixels) of the image to set
 * @param height    The height (in pixels) of the image to set
 * @param filename  The name of the image file to set
 * @param author    The name of the author of the image to set
 * @param authorUrl The url for the author of the image to set
 * @param postUrl   The url for the post of the image to set
 */
@JsonClass(generateAdapter = true)
data class ApiImage(
    @Json(name = "id")
    val id: Long,

    @Json(name = "format")
    val format: String,

    @Json(name = "width")
    val width: Int,

    @Json(name = "height")
    val height: Int,

    @Json(name = "filename")
    val filename: String,

    @Json(name = "author")
    val author: String,

    @Json(name = "author_url")
    val authorUrl: String,

    @Json(name = "post_url")
    val postUrl: String
) {
    /**
     * Returns a new instance of [Image] for which properties are equals to this [ApiImage]
     * properties.
     *
     * @return a new instance of [Image] for which properties are equals to this [ApiImage]
     * properties
     */
    fun toImage(): Image = Image(
        id = id,
        width = width,
        height = height,
        filename = filename,
        author = author,
        type = format
    )
}

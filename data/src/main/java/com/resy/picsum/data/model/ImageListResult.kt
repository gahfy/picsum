package com.resy.picsum.data.model

/**
 * Model for the result of requesting the image list.
 *
 * @property datasource The type of datasource from which the result comes from
 * @property result     The expected result
 *
 * @constructor Instantiates a new [ImageListResult]
 *
 * @param datasource The type of datasource from which the result comes from to set
 * @param result     The expected result to set
 */
data class ImageListResult(
    val datasource: Datasource,
    val result: List<Image>
)

package com.resy.picsum.framework.android

import android.content.Context
import androidx.annotation.StringRes

/**
 * Contract interface for providing resources
 */
interface ResourceProvider {
    /**
     * Returns the string with the given id from the resources.
     *
     * @param resId the unique identifier of the string to return
     *
     * @return the string with the given id from the resources
     */
    fun getString(@StringRes resId: Int): String
}

/**
 * Implementation of resource provider which uses the context to provide resources
 *
 * @property context Context in which the application is running
 *
 * @constructor Instantiates a new [ResourceProviderImpl].
 *
 * @param context Context in which the application is running to set
 */
class ResourceProviderImpl(
    private val context: Context
): ResourceProvider {
    /**
     * Returns the string with the given id from the resources.
     *
     * @param resId the unique identifier of the string to return
     *
     * @return the string with the given id from the resources
     */
    override fun getString(@StringRes resId: Int) =
        context.getString(resId)
}
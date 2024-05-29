package com.resy.picsum.framework.di

import android.content.Context
import com.resy.picsum.framework.android.ResourceProvider
import com.resy.picsum.framework.android.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Dependency module which provides everything related to Android context.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object AndroidModule {
    /**
     * Provides the resource provider
     *
     * @param context Context in which the application is running
     *
     * @return the resource provider
     */
    @Provides
    @ViewModelScoped
    fun providesResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider {
        return ResourceProviderImpl(context)
    }
}

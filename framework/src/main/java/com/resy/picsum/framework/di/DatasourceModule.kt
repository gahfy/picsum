package com.resy.picsum.framework.di

import com.resy.picsum.data.datasource.LocalImageDatasource
import com.resy.picsum.data.datasource.RemoteImageDatasource
import com.resy.picsum.framework.api.service.PicsumApiService
import com.resy.picsum.framework.database.dao.ImageDao
import com.resy.picsum.framework.datasource.LocalImageDatasourceImpl
import com.resy.picsum.framework.datasource.RemoteImageDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Dependency module which provides everything related to datasources.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object DatasourceModule {

    /**
     * Provides the local datasource for images to be used in the application.
     *
     * @param imageDao the database access object to access the images from the local database
     *
     * @return the local datasource for images to be used in the application
     */
    @Provides
    @ViewModelScoped
    fun provideLocalImageDatasource(
        imageDao: ImageDao
    ): LocalImageDatasource {
        return LocalImageDatasourceImpl(
            imageDao = imageDao
        )
    }

    /**
     * Provides the remote datasource for images to be used in the application.
     *
     * @param apiService the API service to access the images from the API
     *
     * @return the remote datasource for images to be used in the application
     */
    @Provides
    @ViewModelScoped
    fun provideRemoteImageDatasource(
        apiService: PicsumApiService
    ): RemoteImageDatasource {
        return RemoteImageDatasourceImpl(
            apiService = apiService
        )
    }
}

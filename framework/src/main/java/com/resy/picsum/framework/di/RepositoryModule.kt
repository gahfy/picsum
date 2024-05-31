package com.resy.picsum.framework.di

import com.resy.picsum.data.datasource.CachedFileDatasource
import com.resy.picsum.data.datasource.LocalImageDatasource
import com.resy.picsum.data.datasource.RemoteImageDatasource
import com.resy.picsum.data.repository.CachedFileRepository
import com.resy.picsum.data.repository.CachedFileRepositoryImpl
import com.resy.picsum.data.repository.ImageRepository
import com.resy.picsum.data.repository.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Dependency module which provides everything related to repositories.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    /**
     * Provides the Image repository to be used in the application.
     *
     * @param localImageDatasource  the local datasource to access images
     * @param remoteImageDatasource the remote datasource to access images
     *
     * @return the Image repository to be used in the application
     */
    @Provides
    @ViewModelScoped
    fun provideImageRepository(
        localImageDatasource: LocalImageDatasource,
        remoteImageDatasource: RemoteImageDatasource
    ): ImageRepository {
        return ImageRepositoryImpl(
            localDatasource = localImageDatasource,
            remoteDatasource = remoteImageDatasource
        )
    }

    /**
     * Provides the cached file repository to be used in the application
     *
     * @param cachedFileDatasource the datasource to access cached files
     *
     * @return the cached file repository to be used in the application
     */
    @Provides
    @ViewModelScoped
    fun provideCachedFileRepository(
        cachedFileDatasource: CachedFileDatasource
    ): CachedFileRepository {
        return CachedFileRepositoryImpl(
            cachedFileDatasource = cachedFileDatasource
        )
    }
}

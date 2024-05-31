package com.resy.picsum.framework.di

import com.resy.picsum.data.repository.CachedFileRepository
import com.resy.picsum.data.repository.ImageRepository
import com.resy.picsum.domain.usecase.GetCachedFileUseCase
import com.resy.picsum.domain.usecase.GetCachedFileUseCaseImpl
import com.resy.picsum.domain.usecase.GetImageListUseCase
import com.resy.picsum.domain.usecase.GetImageListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Dependency module which provides everything related to use cases.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    /**
     * Provides the use case to retrieve the images.
     *
     * @param repository the repository for images to be used by the use case
     *
     * @return the use case to retrieve the images
     */
    @Provides
    @ViewModelScoped
    fun provideGetImageUseCase(
        repository: ImageRepository
    ) : GetImageListUseCase {
        return GetImageListUseCaseImpl(
            repository
        )
    }

    /**
     * Provides the use case to get cached file results.
     *
     * @param repository the repository for cached file results to be used by the use case
     *
     * @return the use case to get cached file results
     */
    @Provides
    @ViewModelScoped
    fun provideGetCachedFileUseCase(
        repository: CachedFileRepository
    ): GetCachedFileUseCase {
        return GetCachedFileUseCaseImpl(
            repository
        )
    }
}

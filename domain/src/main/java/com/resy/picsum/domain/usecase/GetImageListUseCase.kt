package com.resy.picsum.domain.usecase

import com.resy.picsum.data.model.ImageListResult
import com.resy.picsum.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

/**
 * Contract to get the flow of list of images.
 */
interface GetImageListUseCase {
    /**
     * Returns the flow of list of images.
     *
     * @return the flow of list of images
     */
    suspend operator fun invoke(): Flow<ImageListResult>
}

/**
 * The use case to get Image list.
 *
 * @property repository the repository from which to retrieve the flow of list of images
 *
 * @constructor Instantiates a new [GetImageListUseCaseImpl]
 *
 * @param repository the repository from which to retrieve the flow of list of images to set
 */
class GetImageListUseCaseImpl(
    private val repository: ImageRepository
) : GetImageListUseCase {

    /**
     * Returns the flow of list of images from the repository.
     *
     * @return the flow of list of images from the repository
     */
    override suspend operator fun invoke(): Flow<ImageListResult> = repository.getImages()
}

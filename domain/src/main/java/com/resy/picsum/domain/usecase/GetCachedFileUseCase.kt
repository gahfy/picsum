package com.resy.picsum.domain.usecase

import com.resy.picsum.data.model.CachedFile
import com.resy.picsum.data.repository.CachedFileRepository

/**
 * Contract to get the flow of cached file results.
 */
interface GetCachedFileUseCase {
    /**
     * Returns the cached file for the given url.
     *
     * @param url  the url for which to get the cached file results
     * @param type the type of file (extension) which is expected
     *
     * @return the cached file for the given url
     */
    suspend operator fun invoke(url: String, type: String): CachedFile
}

/**
 * The use case to get Cached files.
 *
 * @property repository the repository from which to retrieve the cached file
 *
 * @constructor Instantiates a new [GetCachedFileUseCaseImpl]
 *
 * @param repository the repository from which to retrieve the cached file to set
 */
class GetCachedFileUseCaseImpl(
    private val repository: CachedFileRepository
) : GetCachedFileUseCase {

    /**
     * Returns the flow of cached file result for the given url from the repository.
     *
     * @param url  the url for which to get the cached file results
     * @param type the type of file (extension) which is expected
     *
     * @return the flow of cached file result for the given url from the repository
     */
    override suspend operator fun invoke(url: String, type: String): CachedFile = repository.getCachedFile(url, type)
}

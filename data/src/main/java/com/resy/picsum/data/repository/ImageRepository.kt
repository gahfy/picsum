package com.resy.picsum.data.repository

import com.resy.picsum.data.datasource.LocalImageDatasource
import com.resy.picsum.data.datasource.RemoteImageDatasource
import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.data.model.ImageListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * The contract interface for the repository for images
 */
interface ImageRepository {
    /**
     * Returns a flow for the list of images from the repository.
     *
     * @return a flow for the list of images from the repository
     */
    fun getImages(): Flow<ImageListResult>
}

/**
 * Repository which uses local datasource and remote datasource to retrieve the images.
 *
 * @property localDatasource  the local datasource from which to retrieve the images and to which to
 *                            store the images
 * @property remoteDatasource the remote datasource from which to retrieve the images
 *
 * @constructor Instantiates a new [ImageRepositoryImpl].
 *
 * @param localDatasource  the local datasource from which to retrieve the images and to which to
 *                         store the images to set
 * @param remoteDatasource the remote datasource from which to retrieve the images to set
 */
class ImageRepositoryImpl(
    private val localDatasource: LocalImageDatasource,
    private val remoteDatasource: RemoteImageDatasource
): ImageRepository {
    /**
     * Returns the flow of images from the repository.
     *
     * Basically, it first emits the data from the local datasource, then retrieve the data from the
     * remote datasource and uses it to update the local datasource. And finally, emits the final
     * version of the data in the local datasource.
     *
     * @return the flow of images from the repository
     */
    override fun getImages(): Flow<ImageListResult> = flow {
        // Get images from the local datasource and emits it
        val localData = withContext(Dispatchers.IO) {
            localDatasource.getLocalImageList()
        }
        emit(
            ImageListResult(
                datasource = Datasource.LOCAL,
                result = localData
            )
        )

        // Generates a map to associate id of images to images
        val localDataMap = withContext(Dispatchers.Default) {
            localData.associateBy {
                it.id
            }.toMutableMap()
        }

        // Get images from the remote datasource
        val remoteData = withContext(Dispatchers.IO) {
            remoteDatasource.getRemoteImageList()
        }

        val toAdd = mutableListOf<Image>()
        val toUpdate = mutableListOf<Image>()
        val toDelete = mutableListOf<Image>()

        // Populates the list of images to add and to update to the local datasource from
        // differences with remote datasource data
        withContext(Dispatchers.Default) {
            remoteData.forEach {
                if (localDataMap.containsKey(it.id)) {
                    if (localDataMap[it.id] != it) {
                        toUpdate.add(it)
                    }
                    localDataMap.remove(it.id)
                } else {
                    toAdd.add(it)
                }
            }
            toDelete.addAll(localDataMap.values.toList())
        }

        // Updates the local datasource, and emits it
        withContext(Dispatchers.IO) {
            localDatasource.insert(toAdd)
            localDatasource.update(toUpdate)
            localDatasource.delete(toDelete)
        }

        emit(
            ImageListResult(
                datasource = Datasource.REMOTE,
                result = localDatasource.getLocalImageList()
            )
        )
    }
}

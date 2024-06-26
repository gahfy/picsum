package com.resy.picsum.domain.usecase

import com.resy.picsum.data.model.Datasource
import com.resy.picsum.data.model.Image
import com.resy.picsum.data.model.ImageListResult
import com.resy.picsum.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetImageListUseCaseTest {
    val repositoryImages = mutableListOf<ImageListResult>()

    @Before
    fun setup() {
        repositoryImages.clear()
    }

    @Test
    fun testGetImageListUseCase() = runTest{
        val imagesEmittedByRepository = listOf(
            ImageListResult(
                datasource = Datasource.LOCAL,
                result = listOf(
                    Image(0, 5000, 3333, "0.jpeg", "Alejandro Escamilla", "jpeg"),
                    Image(1, 4000, 2000, "1.jpeg", "John Doe", "jpeg")
                )
            ),
            ImageListResult(
                datasource = Datasource.REMOTE,
                result = listOf(
                    Image(2, 3000, 1000, "2.jpeg", "Alice", "jpeg"),
                    Image(3, 2000, 6000, "3.jpeg", "Bob", "jpeg")
                )
            )
        )

        repositoryImages.addAll(imagesEmittedByRepository)

        val useCase = GetImageListUseCaseImpl(repository)

        val imagesEmittedByUseCase = useCase().toList()

        assertEquals(
            "Use case should emit the same data as the repository",
            imagesEmittedByRepository,
            imagesEmittedByUseCase
        )
    }

    private val repository = object: ImageRepository {
        override fun getImages(): Flow<ImageListResult> =
            flow {
                repositoryImages.forEach {
                    emit(it)
                }
            }
    }
}

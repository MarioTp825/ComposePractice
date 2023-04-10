package com.example.artgallery.models.repository

import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.models.poko.ChicagoAPIResponse
import com.example.artgallery.models.poko.ChicagoFullResponse
import com.example.artgallery.models.repository.contracts.ChicagoAPIRepository
import com.example.artgallery.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.utils.NetworkConstants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import retrofit2.Response


class ChicagoAPIRepositoryImplTest {
    private val api: ChicagoAPIService = mockk()
    private val max = NetworkConstants.MAX_ITEMS_PAGINATION
    private lateinit var repository: ChicagoAPIRepository

    @BeforeEach
    fun before() {
        setUpAPIPageResponses()
        setUpAPISingleResponse()
        repository = ChicagoAPIRepositoryImpl(api)
    }

    private fun setUpAPIPageResponses() {
        coEvery {
            api.getArtWorkPage(limit = max, page = 1)
        } returns Response.success(getFakeResponseOne())

        coEvery {
            api.getArtWorkPage(limit = max, page = 2)
        } returns Response.success(getFakeResponseTwo())

        coEvery {
            api.getArtWorkPage(limit = max, page = 3)
        } returns Response.success(getFakeResponseThree())

        coEvery {
            api.getArtWorkPage(limit = max, page = 4)
        } returns Response.error(
            404,
            "error".toResponseBody("application/json".toMediaType())
        )
    }

    private fun setUpAPISingleResponse() {
        coEvery {
            api.getArtWorkDetails(1)
        } returns Response.success(getFullDataFirst())

        coEvery {
            api.getArtWorkDetails(2)
        } returns Response.success(null)

        coEvery {
            api.getArtWorkDetails(3)
        } returns Response.error(
            404,
            "Not found".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getArtWorkDetails(4)
        } returns Response.success(getFullDataFourth())

        coEvery {
            api.getArtWorkDetails(5)
        } returns Response.success(getFullDataFifth())
    }

    @Test
    @Tag("page")
    fun givenFirstPageRequest_whenAPIResponseSuccessful_thenGetDataBack() {
        runBlocking {
            val response = repository.getArtWorksPage()
            assertEquals(
                response,
                ArtHolder.fromBodyToBasicInformationList(getFakeResponseOne())
            )
        }
    }

    @Test
    @Tag("page")
    fun givenSecondPageRequest_whenAPIResponseSuccessful_thenGetDataDifferentData() {
        runBlocking {
            repository.getArtWorksPage()
            val response = repository.getArtWorksPage()
            assertNotEquals(
                response,
                ArtHolder.fromBodyToBasicInformationList(getFakeResponseOne())
            )
        }
    }

    @Test
    @Tag("page")
    fun givenThirdPageRequest_whenAPIResponseSuccessfulButNull_thenThrowNullPointerException() {
        assertThrows(NullPointerException::class.java) {
            runBlocking {
                repository.getArtWorksPage()
                repository.getArtWorksPage()
                repository.getArtWorksPage()
            }
        }
    }

    @Test
    @Tag("page")
    fun givenFourthPageRequest_whenAPIResponseUnsuccessful_thenThrowException() {
        runBlocking {
            try {
                repository.getArtWorksPage()
                repository.getArtWorksPage()
                repository.getArtWorksPage()
            } catch (e: Exception) {
                //Nothing
            }

        }
        assertThrows(Exception::class.java) {
            runBlocking { repository.getArtWorksPage() }
        }
    }

    @Test
    @Tag("detail")
    fun givenFistDetailRequest_whenAPIResponseSuccessful_thenGetData() {
        runBlocking {
            val response = repository.getArtDetails(1)
            assertEquals(
                response,
                ArtHolder.fromBodyToFullInformation(getFullDataFirst().data!!)
            )
        }
    }

    @Test
    @Tag("detail")
    fun givenSecondDetailRequest_whenAPIResponseNull_thenThrowNullException() {
        assertThrows(NullPointerException::class.java) {
            runBlocking {
                repository.getArtDetails(2)
            }
        }
    }

    @Test
    @Tag("detail")
    fun givenThirdDetailRequest_whenAPIResponseUnsuccessful_thenThrowException() {
        assertThrows(Exception::class.java) {
            runBlocking {
                repository.getArtDetails(3)
            }
        }
    }

    @Test
    @Tag("detail")
    fun givenFourthDetailRequest_whenAPIResponseSuccessful_thenGetDifferentData() {
        runBlocking {
            val response = repository.getArtDetails(4)
            assertNotEquals(
                response,
                ArtHolder.fromBodyToFullInformation(getFullDataFirst().data!!)
            )
        }
    }

    @Test
    @Tag("detail")
    fun givenFifthDetailRequest_whenAPIResponseIncorrect_thenThrowException() {
        assertThrows(Exception::class.java) {
            runBlocking {
                repository.getArtDetails(5)
            }
        }
    }

    private fun getFakeResponseOne() = ChicagoAPIResponse(
        data = listOf(
            ChicagoAPIResponse.Data(id = 1),
            ChicagoAPIResponse.Data(id = 2),
            ChicagoAPIResponse.Data(id = 3),
            ChicagoAPIResponse.Data(id = 4),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 4
        )
    )

    private fun getFakeResponseTwo() = ChicagoAPIResponse(
        data = listOf(
            ChicagoAPIResponse.Data(id = 5),
            ChicagoAPIResponse.Data(id = 6),
            ChicagoAPIResponse.Data(id = 7),
            ChicagoAPIResponse.Data(id = 8),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 4
        )
    )

    private fun getFakeResponseThree(): ChicagoAPIResponse? = null

    private fun getFullDataFirst() =
        ChicagoFullResponse(
            data = ChicagoAPIResponse.Data(
                id = 1,
                imageId = "1234"
            )
        )

    private fun getFullDataFourth() =
        ChicagoFullResponse(
            data = ChicagoAPIResponse.Data(
                id = 4,
                imageId = "4321"
            )
        )

    private fun getFullDataFifth() = ChicagoFullResponse(
        data = ChicagoAPIResponse.Data(
            id = 6,
            imageId = "2342"
        )
    )
}
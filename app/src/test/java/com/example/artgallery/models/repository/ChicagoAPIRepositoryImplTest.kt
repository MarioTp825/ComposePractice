package com.example.artgallery.models.repository

import com.example.artgallery.artWork.models.api.ChicagoAPIService
import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.poko.*
import com.example.artgallery.artWork.models.repository.contracts.ChicagoAPIRepository
import com.example.artgallery.artWork.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.utils.NetworkConstants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import retrofit2.Response


class ChicagoAPIRepositoryImplTest {
    private val api: ChicagoAPIService = mockk()
    private val max = NetworkConstants.MAX_ITEMS_PAGINATION
    private lateinit var repository: ChicagoAPIRepository

    @Before
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

        coEvery {
            api.searchArtWork("first", page = 1)
        } returns Response.success(getSearchDataFist())
        coEvery {
            api.searchArtWork("first", page = 2)
        } returns Response.success(getSearchDataSecond())
        coEvery {
            api.searchArtWork("second", page = 1)
        } returns Response.success(getSearchDataThird())
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
    fun givenFirstPageRequest_whenAPIResponseSuccessful_thenGetDataBack() {
        runBlocking {
            val response = repository.getArtWorksPage()
            assertEquals(
                response,
                ArtHolder.fromBodyToFullInformationList(getFakeResponseOne())
            )
        }
    }

    @Test
    fun givenSecondPageRequest_whenAPIResponseSuccessful_thenGetDataDifferentData() {
        runBlocking {
            repository.getArtWorksPage()
            val response = repository.getArtWorksPage()
            assertNotEquals(
                response,
                ArtHolder.fromBodyToFullInformationList(getFakeResponseOne())
            )
        }
    }

    @Test
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
    fun givenFistDetailRequest_whenAPIResponseSuccessful_thenGetData() {
        runBlocking {
            val response = repository.getArtDetails(1)
            assertEquals(
                response,
                ArtHolder.fromBodyToFullInformation(getFullDataFirst().fullData!!)
            )
        }
    }

    @Test
    fun givenSecondDetailRequest_whenAPIResponseNull_thenThrowNullException() {
        assertThrows(NullPointerException::class.java) {
            runBlocking {
                repository.getArtDetails(2)
            }
        }
    }

    @Test
    fun givenThirdDetailRequest_whenAPIResponseUnsuccessful_thenThrowException() {
        assertThrows(Exception::class.java) {
            runBlocking {
                repository.getArtDetails(3)
            }
        }
    }

    @Test
    fun givenFourthDetailRequest_whenAPIResponseSuccessful_thenGetDifferentData() {
        runBlocking {
            val response = repository.getArtDetails(4)
            assertNotEquals(
                response,
                ArtHolder.fromBodyToFullInformation(getFullDataFirst().fullData!!)
            )
        }
    }

    @Test
    fun givenFifthDetailRequest_whenAPIResponseIncorrect_thenThrowException() {
        assertThrows(Exception::class.java) {
            runBlocking {
                repository.getArtDetails(5)
            }
        }
    }

    @Test
    fun givenFistSearchRequest_whenAPIResponseCorrect_thenResetCount() {
        runBlocking {
            repository.getArtWorksPage()
            repository.getArtWorksPage()
            repository.clear()
            val response = repository.search("first")
            assertEquals(
                response,
                ArtHolder.fromSearchBodyToFullInformationList(getSearchDataFist())
            )
        }
    }
    @Test
    fun givenDifferentSearchRequest_whenAPIResponseCorrect_thenResetSearch() {
        runBlocking {
            val resOne = repository.search("first")
            repository.search("first")
            repository.clear()
            val resTwo = repository.search("second")
            assertNotEquals(
                resOne,
                resTwo
            )
        }
    }

    private fun getFakeResponseOne() = ChicagoAPIResponse(
        data = listOf(
            ArtData.FullData(id = 1),
            ArtData.FullData(id = 2),
            ArtData.FullData(id = 3),
            ArtData.FullData(id = 4),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 4
        )
    )

    private fun getFakeResponseTwo() = ChicagoAPIResponse(
        data = listOf(
            ArtData.FullData(id = 5),
            ArtData.FullData(id = 6),
            ArtData.FullData(id = 7),
            ArtData.FullData(id = 8),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 4
        )
    )

    private fun getFakeResponseThree(): ChicagoAPIFullResponse? = null

    private fun getFullDataFirst() =
        ChicagoFullResponse(
            fullData = ArtData.FullData(
                id = 1,
                imageId = "1234"
            )
        )

    private fun getFullDataFourth() =
        ChicagoFullResponse(
            fullData = ArtData.FullData(
                id = 4,
                imageId = "4321"
            )
        )

    private fun getFullDataFifth() = ChicagoFullResponse(
        fullData = ArtData.FullData(
            id = 6,
            imageId = "2342"
        )
    )

    private fun getSearchDataFist() = ChicagoAPISearchResponse(
        data = listOf(
            ArtData.SearchData(
                id = 4,
            ),
            ArtData.SearchData(
                id = 6,
            ),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 2
        )
    )

    private fun getSearchDataSecond() = ChicagoAPISearchResponse(
        data = listOf(
            ArtData.SearchData(
                id = 7,
            ),
            ArtData.SearchData(
                id = 8,
            ),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 2
        )
    )

    private fun getSearchDataThird() = ChicagoAPISearchResponse(
        data = listOf(
            ArtData.SearchData(
                id = 9,
            ),
            ArtData.SearchData(
                id = 10,
            ),
        ),
        pagination = ChicagoAPIResponse.Pagination(
            totalPages = 2
        )
    )
}
package com.example.artgallery.viewModels

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.models.poko.*
import com.example.artgallery.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.utils.NetworkConstants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class ArtWorkViewModelsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    init {
        ArtObjectBox.init(ApplicationProvider.getApplicationContext())
    }

    private lateinit var viewModel: ArtWorkViewModel
    private val max = NetworkConstants.MAX_ITEMS_PAGINATION

    @Before
    fun setUp() {
        viewModel = ArtWorkViewModel(
            repository = ChicagoAPIRepositoryImpl(
                api = setUpService()
            )
        )
    }

    private fun setUpService(): ChicagoAPIService {
        val api = mockk<ChicagoAPIService>()

        setPageResponses(api)
        setSearchResponses(api)
        setSingleResponse(api)

        return api
    }

    private fun setSingleResponse(api: ChicagoAPIService) {
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

    private fun setSearchResponses(api: ChicagoAPIService) {
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

    private fun setPageResponses(api: ChicagoAPIService) {
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

    @Test
    fun givenIdNull_whenGettingDetails_thenReturnNull() {
        runBlocking {
            viewModel.loadPage()
            assertThrows(NullPointerException::class.java) {
                viewModel.findArtById(null)
            }
        }

    }

    @Test
    fun givenSearch_whenDataPreviouslyFetched_thenClearCache() {
        viewModel.loadPage()
        viewModel.loadPage()
        val normalSize = viewModel.basicInformationState.value.artData.toList().size
        viewModel.loadSearchPage("first")
        val searchSize = viewModel.basicInformationState.value.artData.toList().size
        assertNotEquals(normalSize, searchSize)
    }

    @Test
    fun givenDifferentSearch_whenPreviousSearchHasSet_thenClearCache() {
        viewModel.loadPage()
        viewModel.loadPage()
        val normalSize = viewModel.basicInformationState.value.artData.toList().size
        viewModel.loadSearchPage("first")
        val searchSize = viewModel.basicInformationState.value.artData.toList().size
        assertNotEquals(normalSize, searchSize)
    }

    @After
    fun close() {
        ArtObjectBox.store.close()
    }

    private fun getBaseInformation(id: Int) = ArtHolder.ArtFullInformation(
        id = id,
        title = "title",
        author = "author",
        imageId = "id",
        altText = null,
        desc = null,
        lastUpdate = null,
        chips = mapOf(),
        additionalData = mapOf()
    )

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
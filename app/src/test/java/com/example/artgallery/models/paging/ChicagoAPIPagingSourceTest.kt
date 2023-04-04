package com.example.artgallery.models.paging

import androidx.paging.PagingSource
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtWrapper
import com.example.artgallery.models.poko.ChicagoAPIResponse
import com.example.artgallery.utils.NetworkConstants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class ChicagoAPIPagingSourceTest {

    @Test
    fun givenResponse_WhenAPICodeIs404_throwException() {
        val api = mockk<ChicagoAPIService>()

        coEvery {
            api.getArtWork(any(), any())
        } returns Response.error(404, ResponseBody.create(MediaType.get("text/json"), "No data"))

        val pager = ChicagoAPIPagingSource(
            api
        ) {
            //
        }

        runBlocking {
            val result = pager.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = NetworkConstants.MAX_ITEMS_PAGINATION,
                    placeholdersEnabled = false
                )
            )
            val error = (result as PagingSource.LoadResult.Error<Int, ArtWrapper.ArtFullInformation>)
            assertEquals("Not Obtained", error.throwable.message, "Not found")
        }
    }

    @Test
    fun givenEmptyList_WhenAPIResponseSuccessful_thenCallFunctionWithValues() {
        val api = mockk<ChicagoAPIService>()
        val data = mutableListOf<ArtWrapper.ArtFullInformation>()

        coEvery {
            api.getArtWork(any(), any())
        } returns Response.success(
            ChicagoAPIResponse(
                data = listOf(
                    ChicagoAPIResponse.Data()
                )
            )
        )

        val pager = ChicagoAPIPagingSource(
            api
        ) {
            data.addAll(it)
        }

        runBlocking {
            pager.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = NetworkConstants.MAX_ITEMS_PAGINATION,
                    placeholdersEnabled = false
                )
            )
            assert(data.isNotEmpty())
        }
    }

}
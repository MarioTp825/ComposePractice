package com.example.artgallery.models.repository.implementation

import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.models.dto.ArtHolder.ArtFullInformation
import com.example.artgallery.models.poko.*
import com.example.artgallery.models.repository.contracts.ChicagoAPIRepository
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Response

typealias NullCheckAction<T> = (T) -> Boolean
typealias AdditionalCheckAction<T> = (T) -> String?

class ChicagoAPIRepositoryImpl(
    private val api: ChicagoAPIService
) : ChicagoAPIRepository {
    private val nullErrorMessage = "The service response was successful but the body was null"
    private var currentPage = 1
    private var totalPages = 2

    override suspend fun getArtWorksPage(): List<ArtFullInformation> {
        if (this.isAtLastPage) throw IndexOutOfBoundsException("No more pages to query")
        val response = api.getArtWorkPage(
            limit = NetworkConstants.MAX_ITEMS_PAGINATION,
            page = currentPage
        )
        val body = buildArtPageResponseCheck(response)
        updatePages(body)
        return ArtHolder.fromBodyToFullInformationList(body).distinctBy { it.id }
    }

    private fun <T: ArtData> updatePages(body: ChicagoAPIResponse<T>) {
        currentPage++
        totalPages = body.pagination!!.totalPages!!
    }

    private fun buildArtPageResponseCheck(
        response: Response<ChicagoAPIFullResponse>
    ) = checkForUnsuccessfulResponses(response) {
        it.data == null || it.pagination?.totalPages == null
    }

    override suspend fun getArtDetails(id: Int): ArtFullInformation {
        val response = api.getArtWorkDetails(id = id)
        return ArtHolder.fromBodyToFullInformation(
            buildArtDetailResponseCheck(response, id).fullData!!
        )
    }

    override suspend fun search(query: String): List<ArtFullInformation> {
        val response = api.searchArtWork(query = query)
        val body = buildArtSearchPageResponseCheck(response)
        updatePages(body)
        return ArtHolder.fromSearchBodyToFullInformationList(body)
    }

    override fun clear() {
        currentPage = 1
        totalPages = 2
    }

    private fun buildArtSearchPageResponseCheck(
        response: Response<ChicagoAPISearchResponse>
    ) = checkForUnsuccessfulResponses(response) {
        it.data == null || it.pagination?.totalPages == null
    }

    override val isAtLastPage: Boolean
        get() = currentPage >= totalPages

    private fun buildArtDetailResponseCheck(
        response: Response<ChicagoFullResponse>,
        id: Int
    ) = checkForUnsuccessfulResponses(
        response = response,
        additionalCheckAction = {
            if (it.fullData?.id != id)
                "The expected Id was '$id', but '${it.fullData?.id}' was received"
            else null
        }
    ) {
        it.fullData?.id == null || it.fullData.imageId == null
    }


    private inline fun <T> checkForUnsuccessfulResponses(
        response: Response<T>,
        noinline additionalCheckAction: AdditionalCheckAction<T>? = null,
        nullCheck: NullCheckAction<T>
    ): T {
        if (!response.isSuccessful)
            throw Exception("Http Code: ${response.code()}.\nMessage: ${response.message()}")

        val body = response.body()

        if (body == null || nullCheck(body))
            throw NullPointerException(nullErrorMessage)

        additionalCheckAction?.let { action ->
            action(body)?.let {
                throw Exception(it)
            }
        }

        return body
    }
}
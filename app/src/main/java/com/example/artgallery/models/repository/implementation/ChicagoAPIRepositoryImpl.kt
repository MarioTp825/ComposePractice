package com.example.artgallery.models.repository.implementation

import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.models.dto.ArtHolder.ArtBasicInformation
import com.example.artgallery.models.dto.ArtHolder.ArtFullInformation
import com.example.artgallery.models.poko.ChicagoAPIResponse
import com.example.artgallery.models.poko.ChicagoFullResponse
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

    override suspend fun getArtWorksPage(): List<ArtBasicInformation> {
        if (this.isAtLastPage) throw IndexOutOfBoundsException("No more pages to query")
        val response = api.getArtWorkPage(
            limit = NetworkConstants.MAX_ITEMS_PAGINATION,
            page = currentPage
        )
        val body = buildArtPageResponseCheck(response)
        updatePages(body)
        return ArtHolder.fromBodyToBasicInformationList(body).distinctBy { it.id }
    }

    private fun updatePages(body: ChicagoAPIResponse) {
        currentPage++
        totalPages = body.pagination!!.totalPages!!
    }

    private fun buildArtPageResponseCheck(
        response: Response<ChicagoAPIResponse>
    ) = checkForUnsuccessfulResponses(response) {
        it.data == null || it.pagination?.totalPages == null
    }

    override suspend fun getArtDetails(id: Int): ArtFullInformation {
        val response = api.getArtWorkDetails(id = id)
        return ArtHolder.fromBodyToFullInformation(
            buildArtDetailResponseCheck(response, id).data!!
        )
    }

    override val isAtLastPage: Boolean
        get() = currentPage >= totalPages

    private fun buildArtDetailResponseCheck(
        response: Response<ChicagoFullResponse>,
        id: Int
    ) = checkForUnsuccessfulResponses(
        response = response,
        additionalCheckAction = {
            if (it.data?.id != id)
                "The expected Id was '$id', but '${it.data?.id}' was received"
            else null
        }
    ) {
        it.data?.id == null || it.data.imageId == null
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
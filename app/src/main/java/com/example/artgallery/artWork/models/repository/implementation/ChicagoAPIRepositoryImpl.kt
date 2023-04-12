package com.example.artgallery.artWork.models.repository.implementation

import com.example.artgallery.artWork.models.api.ChicagoAPIService
import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.dto.ArtHolder.ArtFullInformation
import com.example.artgallery.artWork.models.poko.*
import com.example.artgallery.artWork.models.repository.contracts.ChicagoAPIRepository
import com.example.artgallery.utils.checkForUnsuccessfulResponses
import retrofit2.Response

class ChicagoAPIRepositoryImpl(
    private val api: ChicagoAPIService
) : ChicagoAPIRepository {
    private var currentPage = 1
    private var totalPages = 2

    override suspend fun getArtWorksPage(): List<ArtFullInformation> {
        if (this.isAtLastPage) throw IndexOutOfBoundsException("No more pages to query")
        val response = api.getArtWorkPage(
            page = currentPage
        )
        val body = buildArtPageResponseCheck(response)
        updatePages(body)
        return ArtHolder.fromBodyToFullInformationList(body).distinctBy { it.id }
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

    override suspend fun search(query: String): List<ArtFullInformation> {
        val response = api.searchArtWork(
            query = query,
            page = currentPage
        )
        val body = buildArtSearchPageResponseCheck(response)
        updatePages(body)
        return ArtHolder.fromSearchBodyToFullInformationList(body)
    }

    private fun buildArtSearchPageResponseCheck(
        response: Response<ChicagoAPISearchResponse>
    ) = checkForUnsuccessfulResponses(response) {
        it.data == null || it.pagination?.totalPages == null
    }

    override fun clear() {
        currentPage = 1
        totalPages = 2
    }

    override val isAtLastPage: Boolean
        get() = currentPage >= totalPages

    private fun <T: ArtData> updatePages(body: ChicagoAPIResponse<T>) {
        currentPage++
        totalPages = body.pagination!!.totalPages!!
    }

}
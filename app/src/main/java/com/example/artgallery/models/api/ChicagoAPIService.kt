package com.example.artgallery.models.api

import com.example.artgallery.models.poko.ChicagoAPIResponse
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChicagoAPIService {
    @GET(NetworkConstants.GET_ARTWORK_URL)
    suspend fun getArtWork(
        @Query(NetworkConstants.ARTWORK_LIMIT_PARAM) limit: Int = NetworkConstants.MAX_ITEMS_PAGINATION,
        @Query(NetworkConstants.ARTWORK_PAGE_PARAM) page: Int
    ): Response<ChicagoAPIResponse>
}
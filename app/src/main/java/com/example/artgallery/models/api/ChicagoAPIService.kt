package com.example.artgallery.models.api

import com.example.artgallery.models.poko.ChicagoAPIResponse
import com.example.artgallery.models.poko.ChicagoFullResponse
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChicagoAPIService {
    @GET("artworks/")
    suspend fun getArtWorkPage(
        @Query(NetworkConstants.ARTWORK_LIMIT_PARAM) limit: Int = NetworkConstants.MAX_ITEMS_PAGINATION,
        @Query(NetworkConstants.ARTWORK_PAGE_PARAM) page: Int
    ): Response<ChicagoAPIResponse>

    @GET("artworks/{id}/")
    suspend fun getArtWorkDetails(
        @Path("id") id: Int
    ): Response<ChicagoFullResponse>
}
//Request{method=GET, url=https://api.artic.edu/api/v1/artworks/254337, tags={class retrofit2.Invocation=com.example.artgallery.models.api.ChicagoAPIService.getArtWorkDetails() [254337]}}
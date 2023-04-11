package com.example.artgallery.models.api

import com.example.artgallery.models.poko.ChicagoAPIFullResponse
import com.example.artgallery.models.poko.ChicagoAPISearchResponse
import com.example.artgallery.models.poko.ChicagoFullResponse
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChicagoAPIService {
    @GET("artworks/")
    suspend fun getArtWorkPage(
        @Query("limit") limit: Int = NetworkConstants.MAX_ITEMS_PAGINATION,
        @Query("page") page: Int
    ): Response<ChicagoAPIFullResponse>

    @GET("artworks/{id}/")
    suspend fun getArtWorkDetails(
        @Path("id") id: Int
    ): Response<ChicagoFullResponse>

    @GET("artworks/search/")
    suspend fun searchArtWork(
        @Query("q") query: String,
        @Query("limit") limit: Int = NetworkConstants.MAX_ITEMS_PAGINATION,
        @Query("page") page: Int,
    ): Response<ChicagoAPISearchResponse>
}
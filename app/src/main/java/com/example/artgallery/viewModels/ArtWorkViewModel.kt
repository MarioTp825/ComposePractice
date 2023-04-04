package com.example.artgallery.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtWrapper
import com.example.artgallery.models.paging.ChicagoAPIPagingSource
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtWorkViewModel : ViewModel() {
    val cache = mutableListOf<ArtWrapper.ArtFullInformation>()
    val pager = Pager(
        config = PagingConfig(
            pageSize = NetworkConstants.MAX_ITEMS_PAGINATION,
            prefetchDistance = NetworkConstants.PREFERRED_DISTANCE_PAGINATION,
        ),
        pagingSourceFactory = {
            ChicagoAPIPagingSource(
                api = Retrofit.Builder()
                .baseUrl(NetworkConstants.GET_ARTWORK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChicagoAPIService::class.java)
            ){
                cache.addAll(it)
            }
        }
    ).flow.cachedIn(viewModelScope)

    fun findArtById(id: Int?): ArtWrapper.ArtFullInformation? {
        id ?: return null
        return cache.firstOrNull { it.id == id }
    }


}
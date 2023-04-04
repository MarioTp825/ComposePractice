package com.example.artgallery.viewModels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.artgallery.models.dto.ArtWrapper
import com.example.artgallery.models.paging.ChicagoAPIPagingSource
import com.example.artgallery.utils.NetworkConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

typealias ArtWorkList = List<ArtWrapper.ArtFullInformation>

class ArtWorkViewModel : ViewModel() {
    private val cache = mutableListOf<ArtWrapper.ArtFullInformation>()
    val pager = Pager(
        config = PagingConfig(
            pageSize = NetworkConstants.MAX_ITEMS_PAGINATION,
            prefetchDistance = NetworkConstants.PREFERRED_DISTANCE_PAGINATION,
        ),
        pagingSourceFactory = {
            ChicagoAPIPagingSource{
                cache.addAll(it)
            }
        }
    ).flow.cachedIn(viewModelScope)

    fun findArtById(id: Int?): ArtWrapper.ArtFullInformation? {
        id ?: return null
        return cache.firstOrNull { it.id == id }
    }


}
package com.example.artgallery.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.models.dto.*
import com.example.artgallery.models.dto.ArtHolder.ArtState
import com.example.artgallery.models.dto.ArtHolder.ArtState.Companion.DONE
import com.example.artgallery.models.dto.ArtHolder.ArtState.Companion.INITIAL_LOADING
import com.example.artgallery.models.dto.ArtHolder.ArtState.Companion.LOADING
import com.example.artgallery.models.dto.database.ArtFullInformationEntity
import com.example.artgallery.models.repository.contracts.ArtDataBaseRepository
import com.example.artgallery.models.repository.contracts.ChicagoAPIRepository
import com.example.artgallery.models.repository.implementation.ArtDataBaseRepositoryImpl
import com.example.artgallery.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.utils.NetworkConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

typealias ArtListService = suspend (String?) -> List<ArtHolder.ArtFullInformation>

class ArtWorkViewModel : ViewModel() {
    private var _query: String? = null
    val isSearch: Boolean get() = _query != null

    private val dataBase: ArtDataBaseRepository = ArtDataBaseRepositoryImpl(
        artBox = ArtObjectBox.boxFor(ArtFullInformationEntity::class.java)
    )
    private val repository: ChicagoAPIRepository = ChicagoAPIRepositoryImpl(
        api = buildChicagoService()
    )

    val basicListWrapper = ArtHolder.fromBasicList()
    private val mutableStateCache = MutableStateFlow(value = ArtHolder.fromBasicList())
    val basicInformationState: StateFlow<BasicInformationWrapper> = mutableStateCache

    private val fullInformationWrapper = ArtHolder.fromFullInformation()
    private val mutableStateDetail = MutableStateFlow(value = ArtHolder.fromFullInformation())
    val artDetailState: StateFlow<FullInformationWrapper> = mutableStateDetail

    val queryFlow: Flow<String>
        get() = flow {
            emit(_query.orEmpty())
        }

    val query: String get() = _query.orEmpty()

    private fun buildChicagoService() = Retrofit.Builder()
        .baseUrl(NetworkConstants.GET_ARTWORK_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChicagoAPIService::class.java)

    fun loadPage() {
        if (_query != null) loadSearchPage(_query)
        else loadNormalPage()
    }

    //ArtList
    private fun loadNormalPage() {
        loadPageFromService(null) {
            repository.getArtWorksPage().onEach {
                it.favorite = isFavorite(it.id)
            }
        }
    }

    fun loadSearchPage(query: String?) {
        if (query == null) {
            loadNormalPage()
            return
        }
        loadPageFromService(query) {
            repository.search(it!!)
        }

    }

    private fun loadPageFromService(query: String?, service: ArtListService) {
        if (isLoading(basicListWrapper.state) || repository.isAtLastPage) return
        if (query != this._query) {
            _query = query
            clearCache()
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                requestPageFromService(service)
            } catch (e: Exception) {
                showPageError(e.message)
            }
        }
    }

    private fun clearCache() {
        repository.clear()
        basicListWrapper.artData = mutableListOf()
    }

    private fun showPageError(message: String?) {
        basicListWrapper.state = ArtState.fromError(message = message)
        mutableStateCache.value = basicListWrapper.copy()
    }

    private suspend fun requestPageFromService(service: ArtListService) {
        basicListWrapper.state = getLoadingState()
        mutableStateCache.value = basicListWrapper.copy()
        fetchPageIntoData(service)
        basicListWrapper.state = DONE
        mutableStateCache.value = basicListWrapper.copy()
    }

    private suspend fun fetchPageIntoData(service: ArtListService) {
        basicListWrapper.artData.addAll(
            service(_query)
        )
        basicListWrapper.artData.distinctBy { it.id }
    }

    fun lastIndex() = basicListWrapper.artData.lastIndex

    private fun isLoading(state: ArtState): Boolean =
        state == INITIAL_LOADING || state == LOADING

    private fun getLoadingState(): ArtState =
        if (basicListWrapper.artData.isEmpty()) INITIAL_LOADING else LOADING

    //Single Art
    fun findArtById(id: Int?) {
        id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                requestArtDetailFromService(id)
            } catch (e: Exception) {
                showArtDetailError(e.message)
            }
        }
    }

    private fun showArtDetailError(message: String?) {
        fullInformationWrapper.state = ArtState.fromError(message = message)
        mutableStateDetail.value = fullInformationWrapper.copy()
    }

    private suspend fun requestArtDetailFromService(id: Int) {
        fullInformationWrapper.state = LOADING
        mutableStateDetail.value = fullInformationWrapper.copy()
        retrieveDetails(id)
        fullInformationWrapper.state = DONE
        mutableStateDetail.value = fullInformationWrapper.copy()
    }

    private suspend fun retrieveDetails(id: Int) {
        fullInformationWrapper.artData = repository.getArtDetails(id = id).apply {
            favorite = isFavorite(id)
        }
    }

    private fun isFavorite(id: Int) = dataBase.getFavorite(id) != null

    fun favoriteState(id: Int): Flow<Boolean> = flow {
        emit(isFavorite(id))
    }

    //DataBase
    fun changeFavorites(remove: Boolean, art: ArtHolder.ArtFullInformation?) {
        art ?: return
        try {
            if (remove) dataBase.removeFromFavorites(art)
            else dataBase.addToFavorites(art)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun updateListFavorite(remove: Boolean, id: Int?) {
        id ?: return
        basicListWrapper.artData.indexOfFirst { it.id == id }.let {
            basicListWrapper.artData[it].favorite = !remove
        }
    }
}
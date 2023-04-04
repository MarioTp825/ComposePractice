package com.example.artgallery.models.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.dto.ArtWrapper
import com.example.artgallery.utils.NetworkConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChicagoAPIPagingSource(val received: (List<ArtWrapper.ArtFullInformation>) -> Unit) :
    PagingSource<Int, ArtWrapper.ArtFullInformation>() {
    private val api = Retrofit.Builder()
        .baseUrl(NetworkConstants.GET_ARTWORK_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChicagoAPIService::class.java)

    override fun getRefreshKey(state: PagingState<Int, ArtWrapper.ArtFullInformation>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtWrapper.ArtFullInformation> {
        return try {
            val previous = params.key ?: 0
            val response = api.getArtWork(
                limit = params.loadSize,
                page = previous
            )
            val body = response.body()

            if (!response.isSuccessful || body == null) {
                return LoadResult.Error(Exception(""))
            }

            val current = body.pagination?.current_page ?: 0
            val total = body.pagination?.total_pages ?: 0
            val result = ArtWrapper.fromBody(body)
            received(result)
            LoadResult.Page(
                data = result,
                prevKey = if (previous == 0) null else previous - 1,
                nextKey = if (current >= total) null else previous + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
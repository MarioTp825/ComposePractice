package com.example.artgallery.models.dto

import android.content.Context
import android.util.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.example.artgallery.models.dto.ArtHolder.Companion.invoke
import com.example.artgallery.models.poko.ArtData
import com.example.artgallery.models.poko.ChicagoAPIFullResponse
import com.example.artgallery.models.poko.ChicagoAPISearchResponse
import com.example.artgallery.utils.getThumbnailScaled
import java.util.Calendar

typealias FullInformationList = MutableList<ArtHolder.ArtFullInformation>
typealias BasicInformationWrapper = ArtHolder.ArtWrapper<FullInformationList>
typealias FullInformationWrapper = ArtHolder.ArtWrapper<ArtHolder.ArtFullInformation?>

sealed class ArtHolder {

    data class ArtFullInformation(
        val id: Int,
        val title: String,
        val author: String,
        val imageId: String,
        val altText: String?,
        val desc: String?,
        private val lastUpdate: Calendar?,
        private val base64: String? = null,
        private val size: Size? = null,
        val chips: Map<String, List<String>>,
        val additionalData: Map<String, String>,
        var favorite: Boolean = false,
    ) {
        val imageUrl: String get() = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"

        val basic: ArtBasicInformation
            get() = ArtBasicInformation(
                id = id,
                title = title,
                author = author,
                imageId = imageId,
                alt = altText,
                size = size,
                base64 = base64
            )
    }

    data class ArtBasicInformation(
        val id: Int,
        val title: String,
        val author: String,
        private val imageId: String,
        val alt: String? = null,
        private val base64: String? = null,
        private val size: Size? = null,
    ) {
        val imageUrl: String get() = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"

        val isImageIdEmpty: Boolean get() = imageId.isEmpty()

        fun getBitmap(context: Context): ImageBitmap? =
            if (base64 == null || size == null) null
            else getThumbnailScaled(context = context, base64, size, 50)
    }

    sealed class ArtState(val id: Int, open val msg: String?) {
        class Loading : ArtState(msg = null, id = LOADING_ID)
        class InitialLoading : ArtState(msg = "Loading...", id = INITIAL_LOADING_ID)
        class Done : ArtState(msg = null, id = DONE_ID)
        class Error(private val message: String?) : ArtState(msg = message, id = ERROR_ID) {
            override val msg: String
                get() = message ?: "Unknown Error"
        }

        companion object {
            const val LOADING_ID: Int = 0
            const val INITIAL_LOADING_ID: Int = 1
            const val DONE_ID: Int = 2
            const val ERROR_ID: Int = 3

            val LOADING: ArtState = Loading()
            val INITIAL_LOADING: ArtState = InitialLoading()
            val DONE: ArtState = Done()
            fun fromError(message: String?): ArtState = Error(message = message)
        }
    }


    data class ArtWrapper<T>(
        var artData: T,
        var state: ArtState = ArtState.DONE,
    )

    companion object {
        fun fromBasicList() = ArtWrapper<FullInformationList>(
            artData = mutableListOf()
        )

        fun fromFullInformation() = ArtWrapper<ArtFullInformation?>(
            artData = null,
            state = ArtState.LOADING
        )

        fun fromBodyToFullInformationList(apiResponse: ChicagoAPIFullResponse) =
            apiResponse.data?.filterNotNull()?.map { art ->
                fromBodyToFullInformation(art)
            } ?: listOf()

        fun fromBodyToFullInformation(apiResponse: ArtData.FullData) =
            apiResponse.let { art ->
                ArtFullInformation(
                    id = art.id ?: 0,
                    title = art.title(),
                    author = art.artistTitle(),
                    altText = art.thumbnail?.altText,
                    imageId = art.imageId(),
                    lastUpdate = getStringDate(art.sourceUpdatedAt),
                    desc = art.provenanceText,
                    base64 = art.thumbnail?.lqip?.split("base64,")?.last(),
                    size = art.thumbnail?.height?.let { h ->
                        art.thumbnail.width?.let { w ->
                            Size(w, h)
                        }
                    },
                    chips = mapOf(
                        "Categories" to art.categoryTitles.removeNullEntries,
                        "Classifications" to art.classificationTitles.removeNullEntries,
                    ),
                    additionalData = mapOf(
                        "Display Date" to art.dateDisplay(),
                        "Exhibition Place" to art.artistDisplay(),
                        "Origin Place" to art.placeOfOrigin(),
                        "Size" to art.dimensions(),
                        "Credit Line" to art.creditLine(),
                        "Verification Level" to art.publishingVerificationLevel(),
                        "Public Domain" to if (art.isPublicDomain == true) "Yes" else "No",
                        "Type" to art.artworkTypeTitle(),
                    ),
                )
            }

        private val List<String?>?.removeNullEntries get() = this?.filterNotNull() ?: listOf()

        private fun getStringDate(sourceUpdatedAt: String?): Calendar? {
            sourceUpdatedAt ?: return null
            if (!sourceUpdatedAt.contains("T")) return null

            val data = sourceUpdatedAt.split("T")[0].split("-")
            if (data.size != 3) return null
            return Calendar.getInstance().apply {
                this[Calendar.DAY_OF_MONTH] = data.last().toInt()
                this[Calendar.MONTH] = data[1].toInt()
                this[Calendar.YEAR] = data.first().toInt()
            }
        }

        private operator fun String?.invoke() = this.orEmpty()
        fun fromSearchBodyToFullInformationList(apiResponse: ChicagoAPISearchResponse): List<ArtFullInformation> =
            apiResponse.data?.filterNotNull()?.map { art ->
                fromSearchBodyToFullInformation(art)
            } ?: listOf()

        private fun fromSearchBodyToFullInformation(apiResponse: ArtData.SearchData) =
            apiResponse.let { art ->
                ArtFullInformation(
                    id = art.id ?: 0,
                    title = art.title(),
                    author = "Unknown",
                    altText = art.thumbnail?.altText,
                    imageId = "",
                    lastUpdate = null,
                    desc = null,
                    chips = mapOf(),
                    additionalData = mapOf(),
                    base64 = art.thumbnail?.lqip?.split("base64,")?.last(),
                    size = art.thumbnail?.height?.let { h ->
                        art.thumbnail.width?.let { w ->
                            Size(w, h)
                        }
                    },
                )
            }


    }
}

operator fun BasicInformationWrapper.get(index: Int) = this.artData[index]
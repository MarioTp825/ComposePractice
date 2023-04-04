package com.example.artgallery.models.dto

import com.example.artgallery.models.poko.ChicagoAPIResponse
import java.util.Calendar

data class ArtWrapper(
    val artList: List<ArtFullInformation>,
    val pages: Int,
    val error: String? = null
) {
    data class ArtFullInformation(
        val id: Int,
        val title: String,
        val author: String,
        val imageId: String,
        val altText: String?,
        private val lastUpdate: Calendar?,
        val additionalData: Map<String, String>,
    ) {
        val imageUrl: String get() = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"

        val basic: ArtBasicInformation
            get() = ArtBasicInformation(
                id = id,
                title = title,
                author = author,
                imageId = imageId,
                alt = altText,
            )

        val updatedSince: String
            get() = if (lastUpdate == null) ""
            else "$day/$month/$year"

        private val day get() = lastUpdate?.let { Calendar.DAY_OF_MONTH }
        private val month get() = lastUpdate?.let { Calendar.MONTH }
        private val year get() = lastUpdate?.let { Calendar.YEAR }
    }

    companion object {
        fun fromBody(apiResponse: ChicagoAPIResponse) =
            apiResponse.data?.filterNotNull()?.map { art ->
                ArtFullInformation(
                    id = art.id ?: 0,
                    title = art.title(),
                    author = art.artist_title(),
                    altText = art.thumbnail?.alt_text,
                    imageId = art.image_id(),
                    lastUpdate = getStringDate(art.source_updated_at),
                    additionalData = mapOf(
                        "Description" to art.provenance_text(),
                        "Display Date" to art.date_display(),
                        "Exhibition Place" to art.artist_display(),
                        "Origin Place" to art.place_of_origin(),
                        "Size" to art.dimensions(),
                        "Credit Line" to art.credit_line(),
                        "Verification Level" to art.publishing_verification_level(),
                        "Public Domain" to if(art.is_public_domain == true) "Yes" else "No",
                        "Type" to art.artwork_type_title(),
                        "Categories" to data(art.category_titles)(),
                        "Classifications" to data(art.classification_titles)()
                    ),
                )
            } ?: listOf()

        private fun data(art: List<String?>?) =
            art?.filterNotNull()?.joinToString(", ")

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
    }
}
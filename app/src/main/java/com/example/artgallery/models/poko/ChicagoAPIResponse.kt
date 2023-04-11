package com.example.artgallery.models.poko

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

data class ChicagoFullResponse(
    @SerializedName("data")
    val fullData: ArtData.FullData? = null
)


open class ArtData{
    @Keep
    data class Thumbnail(
        @SerializedName("alt_text")
        val altText: String? = null,
        @SerializedName("height")
        val height: Int? = null,
        @SerializedName("lqip")
        val lqip: String? = null,
        @SerializedName("width")
        val width: Int? = null
    )

    @Keep
    data class SearchData(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("timestamp")
        val timestamp: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("api_link")
        val apiLink: String? = null,
        @SerializedName("api_model")
        val apiModel: String? = null,
        @SerializedName("is_boosted")
        val isBoosted: Boolean? = null,
        @SerializedName("_score")
        val score: Double? = null,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail? = null,
    ): ArtData()

    @Keep
    data class FullData(
        @SerializedName("alt_artist_ids")
        val altArtistIds: List<Any?>? = null,
        @SerializedName("alt_classification_ids")
        val altClassificationIds: List<String?>? = null,
        @SerializedName("alt_image_ids")
        val altImageIds: List<Any?>? = null,
        @SerializedName("alt_material_ids")
        val altMaterialIds: List<String?>? = null,
        @SerializedName("alt_style_ids")
        val altStyleIds: List<Any?>? = null,
        @SerializedName("alt_subject_ids")
        val altSubjectIds: List<String?>? = null,
        @SerializedName("alt_technique_ids")
        val altTechniqueIds: List<String?>? = null,
        @SerializedName("alt_titles")
        val altTitles: Any? = null,
        @SerializedName("api_link")
        val apiLink: String? = null,
        @SerializedName("api_model")
        val apiModel: String? = null,
        @SerializedName("artist_display")
        val artistDisplay: String? = null,
        @SerializedName("artist_id")
        val artistId: Int? = null,
        @SerializedName("artist_ids")
        val artistIds: List<Int?>? = null,
        @SerializedName("artist_title")
        val artistTitle: String? = null,
        @SerializedName("artist_titles")
        val artistTitles: List<String?>? = null,
        @SerializedName("artwork_type_id")
        val artworkTypeId: Int? = null,
        @SerializedName("artwork_type_title")
        val artworkTypeTitle: String? = null,
        @SerializedName("boost_rank")
        val boostRank: Any? = null,
        @SerializedName("catalogue_display")
        val catalogueDisplay: Any? = null,
        @SerializedName("category_ids")
        val categoryIds: List<String?>? = null,
        @SerializedName("category_titles")
        val categoryTitles: List<String?>? = null,
        @SerializedName("classification_id")
        val classificationId: String? = null,
        @SerializedName("classification_ids")
        val classificationIds: List<String?>? = null,
        @SerializedName("classification_title")
        val classificationTitle: String? = null,
        @SerializedName("classification_titles")
        val classificationTitles: List<String?>? = null,
        @SerializedName("color")
        val color: Color? = null,
        @SerializedName("colorfulness")
        val colorfulness: Double? = null,
        @SerializedName("copyright_notice")
        val copyrightNotice: Any? = null,
        @SerializedName("credit_line")
        val creditLine: String? = null,
        @SerializedName("date_display")
        val dateDisplay: String? = null,
        @SerializedName("date_end")
        val dateEnd: Int? = null,
        @SerializedName("date_qualifier_id")
        val dateQualifierId: Int? = null,
        @SerializedName("date_qualifier_title")
        val dateQualifierTitle: String? = null,
        @SerializedName("date_start")
        val dateStart: Int? = null,
        @SerializedName("department_id")
        val departmentId: String? = null,
        @SerializedName("department_title")
        val departmentTitle: String? = null,
        @SerializedName("dimensions")
        val dimensions: String? = null,
        @SerializedName("document_ids")
        val documentIds: List<Any?>? = null,
        @SerializedName("exhibition_history")
        val exhibitionHistory: String? = null,
        @SerializedName("fiscal_year")
        val fiscalYear: Int? = null,
        @SerializedName("fiscal_year_deaccession")
        val fiscalYearDeaccession: Any? = null,
        @SerializedName("gallery_id")
        val galleryId: Any? = null,
        @SerializedName("gallery_title")
        val galleryTitle: Any? = null,
        @SerializedName("has_advanced_imaging")
        val hasAdvancedImaging: Boolean? = null,
        @SerializedName("has_educational_resources")
        val hasEducationalResources: Boolean? = null,
        @SerializedName("has_multimedia_resources")
        val hasMultimediaResources: Boolean? = null,
        @SerializedName("has_not_been_viewed_much")
        val hasNotBeenViewedMuch: Boolean? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("image_id")
        val imageId: String? = null,
        @SerializedName("inscriptions")
        val inscriptions: String? = null,
        @SerializedName("internal_department_id")
        val internalDepartmentId: Int? = null,
        @SerializedName("is_boosted")
        val isBoosted: Boolean? = null,
        @SerializedName("is_on_view")
        val isOnView: Boolean? = null,
        @SerializedName("is_public_domain")
        val isPublicDomain: Boolean? = null,
        @SerializedName("is_zoomable")
        val isZoomable: Boolean? = null,
        @SerializedName("latitude")
        val latitude: Any? = null,
        @SerializedName("latlon")
        val latlon: Any? = null,
        @SerializedName("longitude")
        val longitude: Any? = null,
        @SerializedName("main_reference_number")
        val mainReferenceNumber: String? = null,
        @SerializedName("material_id")
        val materialId: String? = null,
        @SerializedName("material_ids")
        val materialIds: List<String?>? = null,
        @SerializedName("material_titles")
        val materialTitles: List<String?>? = null,
        @SerializedName("max_zoom_window_size")
        val maxZoomWindowSize: Int? = null,
        @SerializedName("medium_display")
        val mediumDisplay: String? = null,
        @SerializedName("on_loan_display")
        val onLoanDisplay: Any? = null,
        @SerializedName("place_of_origin")
        val placeOfOrigin: String? = null,
        @SerializedName("provenance_text")
        val provenanceText: String? = null,
        @SerializedName("publication_history")
        val publicationHistory: String? = null,
        @SerializedName("publishing_verification_level")
        val publishingVerificationLevel: String? = null,
        @SerializedName("section_ids")
        val sectionIds: List<Any?>? = null,
        @SerializedName("section_titles")
        val sectionTitles: List<Any?>? = null,
        @SerializedName("site_ids")
        val siteIds: List<Any?>? = null,
        @SerializedName("sound_ids")
        val soundIds: List<Any?>? = null,
        @SerializedName("source_updated_at")
        val sourceUpdatedAt: String? = null,
        @SerializedName("style_id")
        val styleId: String? = null,
        @SerializedName("style_ids")
        val styleIds: List<String?>? = null,
        @SerializedName("style_title")
        val styleTitle: String? = null,
        @SerializedName("style_titles")
        val styleTitles: List<String?>? = null,
        @SerializedName("subject_id")
        val subjectId: String? = null,
        @SerializedName("subject_ids")
        val subjectIds: List<String?>? = null,
        @SerializedName("subject_titles")
        val subjectTitles: List<String?>? = null,
        @SerializedName("suggest_autocomplete_all")
        val suggestAutocompleteAll: List<SuggestAutocompleteAll?>? = null,
        @SerializedName("technique_id")
        val techniqueId: String? = null,
        @SerializedName("technique_ids")
        val techniqueIds: List<String?>? = null,
        @SerializedName("technique_titles")
        val techniqueTitles: List<String?>? = null,
        @SerializedName("term_titles")
        val termTitles: List<String?>? = null,
        @SerializedName("text_ids")
        val textIds: List<Any?>? = null,
        @SerializedName("theme_titles")
        val themeTitles: List<String?>? = null,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail? = null,
        @SerializedName("timestamp")
        val timestamp: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("updated_at")
        val updatedAt: String? = null,
        @SerializedName("video_ids")
        val videoIds: List<Any?>? = null
    ): ArtData() {
        @Keep
        data class Color(
            @SerializedName("h")
            val h: Int? = null,
            @SerializedName("l")
            val l: Int? = null,
            @SerializedName("percentage")
            val percentage: Double? = null,
            @SerializedName("population")
            val population: Int? = null,
            @SerializedName("s")
            val s: Int? = null
        )

        @Keep
        data class SuggestAutocompleteAll(
            @SerializedName("contexts")
            val contexts: Contexts? = null,
            @SerializedName("input")
            val input: List<String?>? = null,
            @SerializedName("weight")
            val weight: Int? = null
        ) {
            @Keep
            data class Contexts(
                @SerializedName("groupings")
                val groupings: List<String?>? = null
            )
        }
    }
}

@Keep
data class ChicagoAPIResponse<T: ArtData>(
    @SerializedName("config")
    val config: Config? = null,
    @SerializedName("data")
    val data: List<T?>? = null,
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null
) {
    @Keep
    data class Config(
        @SerializedName("iiif_url")
        val iiifUrl: String? = null,
        @SerializedName("website_url")
        val websiteUrl: String? = null
    )

    @Keep
    data class Info(
        @SerializedName("license_links")
        val licenseLinks: List<String?>? = null,
        @SerializedName("license_text")
        val licenseText: String? = null,
        @SerializedName("version")
        val version: String? = null
    )

    @Keep
    data class Pagination(
        @SerializedName("current_page")
        val currentPage: Int? = null,
        @SerializedName("limit")
        val limit: Int? = null,
        @SerializedName("next_url")
        val nextUrl: String? = null,
        @SerializedName("offset")
        val offset: Int? = null,
        @SerializedName("prev_url")
        val prevUrl: String? = null,
        @SerializedName("total")
        val total: Int? = null,
        @SerializedName("total_pages")
        val totalPages: Int? = null
    )
}

typealias ChicagoAPIFullResponse = ChicagoAPIResponse<ArtData.FullData>
typealias ChicagoAPISearchResponse = ChicagoAPIResponse<ArtData.SearchData>
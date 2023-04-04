package com.example.artgallery.models.poko


import androidx.annotation.Keep

@Keep
data class ChicagoAPIResponse(
    val config: Config? = null,
    val `data`: List<Data?>? = null,
    val info: Info? = null,
    val pagination: Pagination? = null
) {
    @Keep
    data class Config(
        val iiif_url: String? = null,
        val website_url: String? = null
    )

    @Keep
    data class Data(
        val alt_artist_ids: List<Int?>? = null,
        val alt_classification_ids: List<String?>? = null,
        val alt_image_ids: List<String?>? = null,
        val alt_material_ids: List<String?>? = null,
        val alt_style_ids: List<String?>? = null,
        val alt_subject_ids: List<Any?>? = null,
        val alt_technique_ids: List<Any?>? = null,
        val alt_titles: List<String?>? = null,
        val api_link: String? = null,
        val api_model: String? = null,
        val artist_display: String? = null,
        val artist_id: Int? = null,
        val artist_ids: List<Int?>? = null,
        val artist_title: String? = null,
        val artist_titles: List<String?>? = null,
        val artwork_type_id: Int? = null,
        val artwork_type_title: String? = null,
        val boost_rank: Any? = null,
        val catalogue_display: Any? = null,
        val category_ids: List<String?>? = null,
        val category_titles: List<String?>? = null,
        val classification_id: String? = null,
        val classification_ids: List<String?>? = null,
        val classification_title: String? = null,
        val classification_titles: List<String?>? = null,
        val color: Color? = null,
        val colorfulness: Double? = null,
        val copyright_notice: Any? = null,
        val credit_line: String? = null,
        val date_display: String? = null,
        val date_end: Int? = null,
        val date_qualifier_id: Int? = null,
        val date_qualifier_title: String? = null,
        val date_start: Int? = null,
        val department_id: String? = null,
        val department_title: String? = null,
        val dimensions: String? = null,
        val document_ids: List<Any?>? = null,
        val exhibition_history: String? = null,
        val fiscal_year: Int? = null,
        val fiscal_year_deaccession: Any? = null,
        val gallery_id: Any? = null,
        val gallery_title: Any? = null,
        val has_advanced_imaging: Boolean? = null,
        val has_educational_resources: Boolean? = null,
        val has_multimedia_resources: Boolean? = null,
        val has_not_been_viewed_much: Boolean? = null,
        val id: Int? = null,
        val image_id: String? = null,
        val inscriptions: String? = null,
        val internal_department_id: Int? = null,
        val is_boosted: Boolean? = null,
        val is_on_view: Boolean? = null,
        val is_public_domain: Boolean? = null,
        val is_zoomable: Boolean? = null,
        val latitude: Any? = null,
        val latlon: Any? = null,
        val longitude: Any? = null,
        val main_reference_number: String? = null,
        val material_id: String? = null,
        val material_ids: List<String?>? = null,
        val material_titles: List<String?>? = null,
        val max_zoom_window_size: Int? = null,
        val medium_display: String? = null,
        val on_loan_display: Any? = null,
        val place_of_origin: String? = null,
        val provenance_text: String? = null,
        val publication_history: String? = null,
        val publishing_verification_level: String? = null,
        val section_ids: List<Any?>? = null,
        val section_titles: List<Any?>? = null,
        val site_ids: List<Any?>? = null,
        val sound_ids: List<Any?>? = null,
        val source_updated_at: String? = null,
        val style_id: String? = null,
        val style_ids: List<String?>? = null,
        val style_title: String? = null,
        val style_titles: List<String?>? = null,
        val subject_id: String? = null,
        val subject_ids: List<String?>? = null,
        val subject_titles: List<String?>? = null,
        val suggest_autocomplete_all: List<SuggestAutocompleteAll?>? = null,
        val technique_id: String? = null,
        val technique_ids: List<String?>? = null,
        val technique_titles: List<String?>? = null,
        val term_titles: List<String?>? = null,
        val text_ids: List<Any?>? = null,
        val theme_titles: List<Any?>? = null,
        val thumbnail: Thumbnail? = null,
        val timestamp: String? = null,
        val title: String? = null,
        val updated_at: String? = null,
        val video_ids: List<Any?>? = null
    ) {
        @Keep
        data class Color(
            val h: Int? = null,
            val l: Int? = null,
            val percentage: Double? = null,
            val population: Int? = null,
            val s: Int? = null
        )

        @Keep
        data class SuggestAutocompleteAll(
            val contexts: Contexts? = null,
            val input: List<String?>? = null,
            val weight: Int? = null
        ) {
            @Keep
            data class Contexts(
                val groupings: List<String?>? = null
            )
        }

        @Keep
        data class Thumbnail(
            val alt_text: String? = null,
            val height: Int? = null,
            val lqip: String? = null,
            val width: Int? = null
        )
    }

    @Keep
    data class Info(
        val license_links: List<String?>? = null,
        val license_text: String? = null,
        val version: String? = null
    )

    @Keep
    data class Pagination(
        val current_page: Int? = null,
        val limit: Int? = null,
        val next_url: String? = null,
        val offset: Int? = null,
        val total: Int? = null,
        val total_pages: Int? = null
    )
}
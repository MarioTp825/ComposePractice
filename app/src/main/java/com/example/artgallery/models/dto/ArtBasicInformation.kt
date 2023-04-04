package com.example.artgallery.models.dto

data class ArtBasicInformation(
    val id: Int,
    val title: String,
    val author: String,
    private val imageId: String,
    val alt: String? = null
) {
    val imageUrl: String get() = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
}
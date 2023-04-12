package com.example.artgallery.artWork.view.screens

sealed class AppScreen(val route: String) {
    object ArtGalleryScreen : AppScreen("home")
    object ArtDetailScreen : AppScreen("art/{id}") {
        fun getRoute(id: Int): String = "art/$id"
    }
}

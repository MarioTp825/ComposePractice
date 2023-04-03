package com.example.artgallery.models.dto

import android.content.Context
import android.util.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.example.artgallery.utils.getDp
import com.example.artgallery.utils.getThumbnailScaled

data class ArtBasicInformation(
    val id: Int,
    val title: String,
    val author: String,
    private val imageBase64: String,
    val size: Size,
    val alt: String? = null
) {
    fun getImageFromBase64(context: Context): ImageBitmap = getThumbnailScaled(
        base64 = imageBase64,
        size = size,
        maxSize = getDp(context, 100f),
        context = context
    )
}
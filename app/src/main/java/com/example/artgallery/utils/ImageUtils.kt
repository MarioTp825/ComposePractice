package com.example.artgallery.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Size
import android.util.TypedValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.math.max

fun getThumbnailScaled(context: Context, base64: String, size: Size, maxSize: Int): ImageBitmap {
    val originalImage = decodeImage(base64)
    val newSize = getNewSize(size.toDp(context), maxSize)
    return Bitmap
        .createScaledBitmap(originalImage, newSize.width, newSize.height, false)
        .asImageBitmap()
}

private fun getNewSize(size: Size, maxSize: Int): Size {
    val factor = maxSize.toFloat() / max(size.width, size.height)
    return Size(
        (size.width * factor).toInt(),
        (size.height * factor).toInt()
    )
}

fun Size.toDp(context: Context) = Size(
    getDp(context, this.width.toFloat()),
    getDp(context, this.height.toFloat())
)

fun getDp(context: Context, px: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_PX,
    px,
    context.resources.displayMetrics
).toInt()

private fun decodeImage(base64: String): Bitmap = base64.decodeBase64.let {
    BitmapFactory.decodeByteArray(it, 0, it.size)
}

private val String.decodeBase64: ByteArray get() = Base64.decode(this, Base64.DEFAULT)
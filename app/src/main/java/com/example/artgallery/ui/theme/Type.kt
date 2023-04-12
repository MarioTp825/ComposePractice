package com.example.artgallery.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        fontStyle = FontStyle.Normal,
        color = Color.Black
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val cardSubtitleTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        fontStyle = FontStyle.Normal,
        color = Color.Black
    )

val cardTitleTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.15.sp,
        color = Color.Black
    )

val sectionContent: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        fontStyle = FontStyle.Normal,
        color = Color.Black
    )
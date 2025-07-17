package com.gauravbajaj.reelly.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class Subtitle(
    val startTime: Long, // in milliseconds
    val endTime: Long,   // in milliseconds
    val text: String,
    val style: SubtitleStyle? = null
)

data class SubtitleStyle(
    val fontSize: TextUnit = 16.sp,
    val color: Color = Color.White,
    val backgroundColor: Color = Color.Black.copy(alpha = 0.7f),
    val position: SubtitlePosition = SubtitlePosition.BOTTOM_CENTER
)

enum class SubtitlePosition {
    TOP_LEFT, TOP_CENTER, TOP_RIGHT,
    MIDDLE_LEFT, MIDDLE_CENTER, MIDDLE_RIGHT,
    BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
}
val sampleSubtitles = listOf(
    Subtitle(startTime = 0L, endTime = 3000L, text = "Welcome to the video!"),
    Subtitle(startTime = 5000L, endTime = 8000L, text = "This is a subtitle at 5 seconds"),
    Subtitle(startTime = 10000L, endTime = 15000L, text = "Another subtitle appears here"),
    Subtitle(startTime = 20000L, endTime = 25000L, text = "Custom styled text",
        style = SubtitleStyle(
            fontSize = 20.sp,
            color = Color.Yellow,
            position = SubtitlePosition.TOP_CENTER
        )
    )
)
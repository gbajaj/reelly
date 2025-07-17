package com.gauravbajaj.reelly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gauravbajaj.reelly.data.model.Subtitle
import com.gauravbajaj.reelly.data.model.SubtitlePosition


@Composable
fun SubtitleOverlay(
    subtitle: Subtitle?,
    modifier: Modifier = Modifier
) {
    subtitle?.let { sub ->
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = when (sub.style?.position
                ?: SubtitlePosition.BOTTOM_CENTER) {
                SubtitlePosition.TOP_LEFT -> Alignment.TopStart
                SubtitlePosition.TOP_CENTER -> Alignment.TopCenter
                SubtitlePosition.TOP_RIGHT -> Alignment.TopEnd
                SubtitlePosition.MIDDLE_LEFT -> Alignment.CenterStart
                SubtitlePosition.MIDDLE_CENTER -> Alignment.Center
                SubtitlePosition.MIDDLE_RIGHT -> Alignment.CenterEnd
                SubtitlePosition.BOTTOM_LEFT -> Alignment.BottomStart
                SubtitlePosition.BOTTOM_CENTER -> Alignment.BottomCenter
                SubtitlePosition.BOTTOM_RIGHT -> Alignment.BottomEnd
            }
        ) {
            Text(
                text = sub.text,
                fontSize = sub.style?.fontSize ?: 16.sp,
                color = sub.style?.color ?: Color.White,
                modifier = Modifier
                    .background(
                        sub.style?.backgroundColor ?: Color.Black.copy(alpha = 0.7f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
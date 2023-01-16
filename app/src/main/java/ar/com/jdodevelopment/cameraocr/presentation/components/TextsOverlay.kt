package ar.com.jdodevelopment.cameraocr.presentation.components


import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import ar.com.jdodevelopment.cameraocr.common.extension.drawPolygon


@OptIn(ExperimentalTextApi::class)
@Composable
fun OverlayTextLines(
    overlayTextLines: List<OverlayTextLinesViewObject>,
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            overlayTextLines.forEach { overlayTextLine ->
                val cornerPoints = overlayTextLine.cornerPoints
                val topLeftCorner = cornerPoints[0]
                drawPolygon(
                    points = cornerPoints,
                    brush = SolidColor(overlayTextLine.color),
                    style = Stroke(width = 4f),
                )
                rotate(
                    degrees = overlayTextLine.inclination,
                    pivot = Offset(
                        topLeftCorner.x,
                        topLeftCorner.y,
                    )
                ) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = overlayTextLine.text,
                        topLeft = Offset(
                            x = topLeftCorner.x,
                            y = topLeftCorner.y
                                    - overlayTextLine.fontSize * 0.4f // Fix vertical alignment
                        ),
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = overlayTextLine.fontSize.sp,
                            brush = SolidColor(overlayTextLine.color),
                        ),
                    )
                }
            }
        }
    )
}

data class OverlayTextLinesViewObject(
    val text: String,
    val fontSize: Float,
    val inclination: Float,
    val cornerPoints: List<PointF>,
    val color: Color = Color.DarkGray,
)

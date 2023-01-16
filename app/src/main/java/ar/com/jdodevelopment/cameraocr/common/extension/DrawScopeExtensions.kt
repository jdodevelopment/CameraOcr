package ar.com.jdodevelopment.cameraocr.common.extension

import android.graphics.PointF
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill


fun DrawScope.drawPolygon(
    points: List<PointF>,
    brush: Brush,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {
    val path = Path().apply {
        points.forEachIndexed { index, point ->
            if (index == 0) {
                moveTo(point.x, point.y)
            } else {
                lineTo(point.x, point.y)
            }
        }
        close()
    }
    this.drawPath(
        path = path,
        brush = brush,
        style = style,
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}
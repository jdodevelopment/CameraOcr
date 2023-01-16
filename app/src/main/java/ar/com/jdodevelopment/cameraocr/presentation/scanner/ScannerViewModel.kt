package ar.com.jdodevelopment.cameraocr.presentation.scanner


import android.graphics.PointF
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ar.com.jdodevelopment.cameraocr.common.extension.height
import ar.com.jdodevelopment.cameraocr.common.extension.inclination
import ar.com.jdodevelopment.cameraocr.presentation.components.OverlayTextLinesViewObject
import com.google.mlkit.vision.text.Text


class ScannerViewModel : ViewModel() {

    var detectedOverlayTextLines: List<OverlayTextLinesViewObject> by mutableStateOf(emptyList())
        private set

    fun updateTextBlocks(text: Text) {
        detectedOverlayTextLines = text.textBlocks.flatMap {
            it.lines
        }.map {
            mapOverlayTextLinesViewObject(it)
        }
    }

    private fun mapOverlayTextLinesViewObject(line: Text.Line): OverlayTextLinesViewObject {
        return OverlayTextLinesViewObject(
            text = line.text,
            cornerPoints = line.cornerPoints?.map { PointF(it.x.toFloat(), it.y.toFloat()) } ?: listOf(),
            inclination = line.inclination().toFloat(),
            fontSize = line.height().toFloat() * 0.6f,
        )
    }

}
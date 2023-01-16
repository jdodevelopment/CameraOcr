package ar.com.jdodevelopment.cameraocr.presentation.scanner


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ar.com.jdodevelopment.cameraocr.ui.theme.CameraOcrTheme


class ScannerActivity : ComponentActivity() {

    private val viewModel by viewModels<ScannerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraOcrTheme {
                ScannerScreen(
                    viewModel.detectedOverlayTextLines,
                    viewModel::updateTextBlocks
                )
            }
        }
    }
}

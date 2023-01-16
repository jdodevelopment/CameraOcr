package ar.com.jdodevelopment.cameraocr.presentation.components

import android.util.Rational
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
fun CameraPreview(
    analyzer: Analyzer,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    AndroidView(
        modifier = Modifier.aspectRatio(Rational(3, 4).toFloat()),
        factory = {
            val previewView = PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FIT_START
            }
            val executor = ContextCompat.getMainExecutor(context)
            cameraProviderFuture.addListener({
                val targetResolution = android.util.Size(previewView.width, previewView.height)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(targetResolution)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(executor, analyzer)
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                val preview = Preview.Builder()
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val processCameraProvider = cameraProviderFuture.get()
                processCameraProvider.unbindAll()
                processCameraProvider.bindToLifecycle(lifecycleOwner, selector, preview, imageAnalysis)
            }, executor)
            previewView
        }
    )
}

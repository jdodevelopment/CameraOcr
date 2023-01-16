package ar.com.jdodevelopment.cameraocr.presentation.scanner


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.com.jdodevelopment.cameraocr.camera.MlKitTextAnalyzerAnalyzer
import ar.com.jdodevelopment.cameraocr.presentation.components.CameraPreview
import ar.com.jdodevelopment.cameraocr.presentation.components.OverlayTextLines
import ar.com.jdodevelopment.cameraocr.presentation.components.OverlayTextLinesViewObject
import ar.com.jdodevelopment.cameraocr.presentation.components.PermissionsHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.mlkit.vision.text.Text


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    textBlocks: List<OverlayTextLinesViewObject>,
    updateTextBlocks: (Text) -> Unit,
) {
    PermissionsHandler(
        permissions = listOf(Manifest.permission.CAMERA),
        onAllPermissionGranted = {
            Box(
                modifier = Modifier.height(IntrinsicSize.Max)
            ) {
                val analyzer = MlKitTextAnalyzerAnalyzer { text ->
                    updateTextBlocks(text)
                }
                CameraPreview(analyzer = analyzer)
                OverlayTextLines(textBlocks)
            }
        },
        shouldShowRationale = {
            ShowRationaleMessage(it)
        },
        onPermissionsDeclined = {
            ShowPermissionsDeclinedMessage()
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ShowRationaleMessage(multiplePermissionsState: MultiplePermissionsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "You must grant camera permission to be able to scan.",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }) {
            Text("Request permission")
        }
    }
}

@Composable
private fun ShowPermissionsDeclinedMessage() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "You have rejected permissions multiple times. In order to grant permissions you must open the application settings.",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", context.packageName, null)
            context.startActivity(intent)
        }) {
            Text("Open settings")
        }
    }
}
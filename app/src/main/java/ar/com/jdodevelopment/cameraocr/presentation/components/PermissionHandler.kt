package ar.com.jdodevelopment.cameraocr.presentation.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsHandler(
    permissions: List<String>,
    onAllPermissionGranted: @Composable () -> Unit,
    shouldShowRationale: @Composable (multiplePermissionsState: MultiplePermissionsState) -> Unit,
    onPermissionsDeclined: @Composable (multiplePermissionsState: MultiplePermissionsState) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var permissionRequested by remember { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions) {
        permissionRequested = true
    }
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
    if (!permissionRequested) {
        return
    }
    when {
        permissionsState.allPermissionsGranted -> {
            onAllPermissionGranted()
        }
        permissionsState.shouldShowRationale -> {
            shouldShowRationale(permissionsState)
        }
        !permissionsState.allPermissionsGranted && !permissionsState.shouldShowRationale -> {
            onPermissionsDeclined(permissionsState)
        }
    }
}
package com.jyotirmay.mapapplicationdemo.ui.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.jyotirmay.mapapplicationdemo.domain.model.BookingStep
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.domain.model.PointKey
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private val YellowButton = Color(0xFFFFD600)
private val LabelBackground = Color(0xFFE8E8E8)

@OptIn(FlowPreview::class)
@Composable
fun MapScreen(
    uiState: MapUiState,
    onLocationPermissionGranted: () -> Unit,
    onMapCenterChanged: (LatLng) -> Unit,
    onVButtonClick: (LatLng) -> Unit,
    onLabelClick: (PointKey) -> Unit,
    onErrorDismissed: () -> Unit,
    onRecenterToCurrentLocation: () -> Unit,
    onRecenterHandled: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val defaultPosition = LatLng(-34.9285, 138.6007)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            uiState.initialCameraPosition ?: defaultPosition,
            15f,
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            onLocationPermissionGranted()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(uiState.initialCameraPosition) {
        uiState.initialCameraPosition?.let { latLng ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(latLng, 15f),
            )
        }
    }

    LaunchedEffect(uiState.recenterTarget) {
        uiState.recenterTarget?.let { latLng ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(latLng, 15f),
            )
            onRecenterHandled()
        }
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.isMoving }
            .filter { !it }
            .debounce(300)
            .collect {
                onMapCenterChanged(cameraPositionState.position.target)
            }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onErrorDismissed()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerItem(
                label = { Text("History") },
                selected = false,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        onOpenHistory()
                    }
                },
            )
        },
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            )

            MapScreenOverlay(
                uiState = uiState,
                onLabelClick = onLabelClick,
                onVButtonClick = { onVButtonClick(cameraPositionState.position.target) },
                onRecenterToCurrentLocation = onRecenterToCurrentLocation,
            )

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
internal fun MapScreenOverlay(
    uiState: MapUiState,
    onLabelClick: (PointKey) -> Unit,
    onVButtonClick: () -> Unit,
    onRecenterToCurrentLocation: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "📍",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 36.sp,
        )

        AqiOverlay(
            aqi = uiState.currentAqi,
            isLoading = uiState.isLoadingAqi,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp),
        )

        val canRecenter = !uiState.isRecentering && !uiState.isSettingPoint && !uiState.isBooking

        FloatingActionButton(
            onClick = {
                if (canRecenter) {
                    onRecenterToCurrentLocation()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 140.dp, end = 16.dp)
                .alpha(if (canRecenter) 1f else 0.38f),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Go to current location",
            )
        }

        BottomPanel(
            pointA = uiState.pointA,
            pointB = uiState.pointB,
            bookingStep = uiState.bookingStep,
            isSettingPoint = uiState.isSettingPoint || uiState.isRecentering || uiState.isBooking,
            onLabelClick = onLabelClick,
            onVButtonClick = onVButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
private fun AqiOverlay(
    aqi: Int?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "aqi",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
        )
        Text(
            text = when {
                isLoading && aqi == null -> "..."
                aqi != null -> aqi.toString()
                else -> "--"
            },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun BottomPanel(
    pointA: LocationPoint?,
    pointB: LocationPoint?,
    bookingStep: BookingStep,
    isSettingPoint: Boolean,
    onLabelClick: (PointKey) -> Unit,
    onVButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LocationLabelRow(
                label = "A",
                address = pointA?.displayLabel,
                enabled = pointA != null,
                onClick = { onLabelClick(PointKey.A) },
            )
            LocationLabelRow(
                label = "B",
                address = pointB?.displayLabel,
                enabled = pointB != null,
                onClick = { onLabelClick(PointKey.B) },
            )
        }

        Button(
            onClick = onVButtonClick,
            enabled = !isSettingPoint,
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = YellowButton,
                contentColor = Color.Black,
            ),
        ) {
            Text(
                text = when (bookingStep) {
                    BookingStep.SetA -> "Set A"
                    BookingStep.SetB -> "Set B"
                    BookingStep.Book -> "Book"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
private fun LocationLabelRow(
    label: String,
    address: String?,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LabelBackground)
            .then(
                if (enabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = address ?: "",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

private val previewPointA = LocationPoint(
    lat = -34.9285,
    lng = 138.6007,
    addressName = "Adelaide, South Australia",
    aqi = 80,
)

private val previewPointB = LocationPoint(
    lat = -34.9313,
    lng = 138.5967,
    addressName = "North Adelaide, South Australia",
    aqi = 72,
)

@Preview(showBackground = true, name = "Set A")
@Composable
private fun MapScreenSetAPreview() {
    MapApplicationDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
        ) {
            MapScreenOverlay(
                uiState = MapUiState(
                    currentAqi = 80,
                    bookingStep = BookingStep.SetA,
                ),
                onLabelClick = {},
                onVButtonClick = {},
                onRecenterToCurrentLocation = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "Set B")
@Composable
private fun MapScreenSetBPreview() {
    MapApplicationDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
        ) {
            MapScreenOverlay(
                uiState = MapUiState(
                    currentAqi = 80,
                    pointA = previewPointA,
                    bookingStep = BookingStep.SetB,
                ),
                onLabelClick = {},
                onVButtonClick = {},
                onRecenterToCurrentLocation = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "Book")
@Composable
private fun MapScreenBookPreview() {
    MapApplicationDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
        ) {
            MapScreenOverlay(
                uiState = MapUiState(
                    currentAqi = 65,
                    pointA = previewPointA,
                    pointB = previewPointB,
                    bookingStep = BookingStep.Book,
                ),
                onLabelClick = {},
                onVButtonClick = {},
                onRecenterToCurrentLocation = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "AQI Loading")
@Composable
private fun MapScreenAqiLoadingPreview() {
    MapApplicationDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
        ) {
            MapScreenOverlay(
                uiState = MapUiState(
                    isLoadingAqi = true,
                    bookingStep = BookingStep.SetA,
                ),
                onLabelClick = {},
                onVButtonClick = {},
                onRecenterToCurrentLocation = {},
            )
        }
    }
}

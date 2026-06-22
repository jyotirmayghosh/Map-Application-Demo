package com.jyotirmay.mapapplicationdemo.ui.feature.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jyotirmay.mapapplicationdemo.domain.model.BookingStep
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

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
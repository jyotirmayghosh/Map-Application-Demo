package com.jyotirmay.mapapplicationdemo.ui.feature.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.domain.model.PointKey
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

private val previewLocationPoint = LocationPoint(
    lat = -34.9285,
    lng = 138.6007,
    addressName = "Adelaide, South Australia",
    aqi = 80,
)

@Preview(showBackground = true, name = "Location A")
@Composable
private fun LocationDetailScreenPreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.A,
            locationPoint = previewLocationPoint,
            onConfirm = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Location B with nickname")
@Composable
private fun LocationDetailScreenWithNicknamePreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.B,
            locationPoint = previewLocationPoint.copy(nickname = "Home"),
            onConfirm = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Not Found")
@Composable
private fun LocationDetailScreenEmptyPreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.B,
            locationPoint = null,
            onConfirm = {},
            onBack = {},
        )
    }
}
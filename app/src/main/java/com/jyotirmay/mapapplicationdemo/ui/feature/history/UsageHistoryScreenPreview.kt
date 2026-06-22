package com.jyotirmay.mapapplicationdemo.ui.feature.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

private val previewItems = listOf(
    BookingResult(
        id = "book-1",
        pointA = BookedLocation(
            latitude = 36.564,
            longitude = 127.001,
            aqi = 30,
            name = "Seoul A Location",
            nickname = null,
        ),
        pointB = BookedLocation(
            latitude = 36.567,
            longitude = 127.0,
            aqi = 40,
            name = "Seoul B Location",
            nickname = null,
        ),
        price = 10000.0,
    ),
    BookingResult(
        id = "book-2",
        pointA = BookedLocation(
            latitude = 36.577,
            longitude = 127.033,
            aqi = 50,
            name = "Seoul C Location",
            nickname = null,
        ),
        pointB = BookedLocation(
            latitude = 36.567,
            longitude = 127.0,
            aqi = 60,
            name = "Seoul D Location",
            nickname = null,
        ),
        price = 20000.0,
    ),
)

@Preview(showBackground = true, name = "Usage History")
@Composable
private fun UsageHistoryScreenPreview() {
    MapApplicationDemoTheme {
        UsageHistoryScreen(
            uiState = UsageHistoryUiState(
                isLoading = false,
                items = previewItems,
                totalCount = 2,
                totalPrice = 30000.0,
            ),
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Usage History Loading")
@Composable
private fun UsageHistoryScreenLoadingPreview() {
    MapApplicationDemoTheme {
        UsageHistoryScreen(
            uiState = UsageHistoryUiState(isLoading = true),
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Usage History Error")
@Composable
private fun UsageHistoryScreenErrorPreview() {
    MapApplicationDemoTheme {
        UsageHistoryScreen(
            uiState = UsageHistoryUiState(
                isLoading = false,
                errorMessage = "Failed to load booking history",
            ),
            onBack = {},
        )
    }
}
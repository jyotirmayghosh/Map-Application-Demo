package com.jyotirmay.mapapplicationdemo.ui.feature.booking

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

private val previewBookingResult = BookingResult(
    id = "book-preview",
    pointA = BookedLocation(
        latitude = 36.564,
        longitude = 127.001,
        aqi = 30,
        name = "Seoul A Location",
        nickname = "home",
    ),
    pointB = BookedLocation(
        latitude = 36.567,
        longitude = 127.0,
        aqi = 40,
        name = "Seoul B Location",
        nickname = "office",
    ),
    price = 10000.0,
)

@Preview(showBackground = true, name = "Booking Summary")
@Composable
private fun BookingSummaryScreenPreview() {
    MapApplicationDemoTheme {
        BookingSummaryScreen(
            bookingResult = previewBookingResult,
            onContinue = {},
            onBack = {},
        )
    }
}
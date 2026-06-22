package com.jyotirmay.mapapplicationdemo.domain.model

data class LocationPoint(
    val lat: Double,
    val lng: Double,
    val addressName: String,
    val aqi: Int?,
    val nickname: String? = null,
) {
    val displayLabel: String
        get() = nickname?.takeIf { it.isNotBlank() } ?: addressName
}

enum class BookingStep {
    SetA,
    SetB,
    Book,
}

enum class PointKey {
    A,
    B,
}

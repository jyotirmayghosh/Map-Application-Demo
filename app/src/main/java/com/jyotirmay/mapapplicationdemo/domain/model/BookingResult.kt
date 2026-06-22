package com.jyotirmay.mapapplicationdemo.domain.model

data class BookedLocation(
    val latitude: Double,
    val longitude: Double,
    val aqi: Int?,
    val name: String,
    val nickname: String?,
)

data class BookingResult(
    val id: String?,
    val pointA: BookedLocation,
    val pointB: BookedLocation,
    val price: Double,
)

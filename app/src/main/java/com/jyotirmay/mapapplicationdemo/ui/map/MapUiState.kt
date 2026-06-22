package com.jyotirmay.mapapplicationdemo.ui.map

import com.google.android.gms.maps.model.LatLng
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.domain.model.BookingStep
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint

data class MapUiState(
    val currentAqi: Int? = null,
    val isLoadingAqi: Boolean = false,
    val pointA: LocationPoint? = null,
    val pointB: LocationPoint? = null,
    val bookingStep: BookingStep = BookingStep.SetA,
    val isSettingPoint: Boolean = false,
    val errorMessage: String? = null,
    val initialCameraPosition: LatLng? = null,
    val recenterTarget: LatLng? = null,
    val isRecentering: Boolean = false,
    val isBooking: Boolean = false,
    val bookingResult: BookingResult? = null,
    val hasLocationPermission: Boolean = false,
)

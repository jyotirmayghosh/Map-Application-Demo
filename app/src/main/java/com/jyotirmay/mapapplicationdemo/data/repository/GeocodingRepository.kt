package com.jyotirmay.mapapplicationdemo.data.repository

import com.jyotirmay.mapapplicationdemo.BuildConfig
import com.jyotirmay.mapapplicationdemo.data.remote.GeocodingApiService
import com.jyotirmay.mapapplicationdemo.data.remote.dto.ReverseGeocodeDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodingRepository @Inject constructor(
    private val geocodingApiService: GeocodingApiService,
) {

    suspend fun reverseGeocode(lat: Double, lng: Double): ReverseGeocodeDto =
        geocodingApiService.reverseGeocode(
            latitude = lat,
            longitude = lng,
            key = BuildConfig.BDC_API_KEY,
        )
}

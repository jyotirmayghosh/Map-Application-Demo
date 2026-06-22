package com.jyotirmay.mapapplicationdemo.data.repository

import com.jyotirmay.mapapplicationdemo.BuildConfig
import com.jyotirmay.mapapplicationdemo.data.remote.AqiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AqiRepository @Inject constructor(
    private val aqiApiService: AqiApiService,
) {

    suspend fun getAqi(lat: Double, lng: Double): Int? {
        val response = aqiApiService.getAqiByGeo(
            lat = lat,
            lng = lng,
            token = BuildConfig.AQICN_TOKEN,
        )
        if (response.status != "ok") return null
        val aqiValue = response.data?.aqi ?: return null
        return aqiValue.toIntOrNull()
    }
}

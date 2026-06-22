package com.jyotirmay.mapapplicationdemo.data.remote

import com.jyotirmay.mapapplicationdemo.data.remote.dto.ReverseGeocodeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {

    @GET("data/reverse-geocode")
    suspend fun reverseGeocode(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("localityLanguage") localityLanguage: String = "en",
        @Query("key") key: String,
    ): ReverseGeocodeDto
}

package com.jyotirmay.mapapplicationdemo.data.remote

import com.jyotirmay.mapapplicationdemo.data.remote.dto.AqiResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AqiApiService {

    @GET("feed/geo:{lat};{lng}/")
    suspend fun getAqiByGeo(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double,
        @Query("token") token: String,
    ): AqiResponseDto
}

package com.jyotirmay.mapapplicationdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AqiResponseDto(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val data: AqiDataDto?,
)

data class AqiDataDto(
    @SerializedName("aqi")
    val aqi: String?,
)

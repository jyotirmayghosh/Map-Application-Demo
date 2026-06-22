package com.jyotirmay.mapapplicationdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookLocationDto(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("aqi")
    val aqi: Int?,
    @SerializedName("name")
    val name: String,
)

data class BookRequestDto(
    @SerializedName("a")
    val a: BookLocationDto,
    @SerializedName("b")
    val b: BookLocationDto,
)

data class BookResponseDto(
    @SerializedName("a")
    val a: BookLocationDto,
    @SerializedName("b")
    val b: BookLocationDto,
    @SerializedName("price")
    val price: Double,
    @SerializedName("id")
    val id: String? = null,
)

package com.jyotirmay.mapapplicationdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReverseGeocodeDto(
    @SerializedName("locality")
    val locality: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("postcode")
    val postcode: String?,
    @SerializedName("localityInfo")
    val localityInfo: LocalityInfoDto? = null,
)

data class LocalityInfoDto(
    @SerializedName("administrative")
    val administrative: List<AdministrativeDto>? = null,
)

data class AdministrativeDto(
    @SerializedName("order")
    val order: Int,
    @SerializedName("name")
    val name: String,
)

fun ReverseGeocodeDto.formattedAddressName(): String {
    localityInfo?.administrative
        ?.filter { it.name.isNotBlank() }
        ?.sortedByDescending { it.order }
        ?.take(2)
        ?.sortedBy { it.order }
        ?.joinToString(", ") { it.name }
        ?.takeIf { it.isNotBlank() }
        ?.let { return it }

    val localityPart = locality?.takeIf { it.isNotBlank() }
    val cityPart = city?.takeIf { it.isNotBlank() }
    return when {
        localityPart != null && cityPart != null -> "$localityPart, $cityPart"
        localityPart != null -> localityPart
        cityPart != null -> cityPart
        else -> "Unknown location"
    }
}

package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.jyotirmay.mapapplicationdemo.data.repository.DeviceLocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val deviceLocationRepository: DeviceLocationRepository,
) {
    suspend operator fun invoke(): LatLng = deviceLocationRepository.getCurrentLocation()
}

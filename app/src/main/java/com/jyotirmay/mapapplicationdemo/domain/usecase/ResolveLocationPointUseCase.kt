package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.jyotirmay.mapapplicationdemo.data.remote.dto.formattedAddressName
import com.jyotirmay.mapapplicationdemo.data.repository.AqiRepository
import com.jyotirmay.mapapplicationdemo.data.repository.GeocodingRepository
import com.jyotirmay.mapapplicationdemo.di.IoDispatcher
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResolveLocationPointUseCase @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val aqiRepository: AqiRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(lat: Double, lng: Double): Flow<Resource<LocationPoint>> = flow {
        emit(Resource.Loading)
        try {
            val point = withContext(ioDispatcher) {
                val geocode = geocodingRepository.reverseGeocode(lat, lng)
                val aqi = aqiRepository.getAqi(lat, lng)
                LocationPoint(
                    lat = lat,
                    lng = lng,
                    addressName = geocode.formattedAddressName(),
                    aqi = aqi,
                )
            }
            emit(Resource.Success(point))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to resolve location"))
        }
    }
}

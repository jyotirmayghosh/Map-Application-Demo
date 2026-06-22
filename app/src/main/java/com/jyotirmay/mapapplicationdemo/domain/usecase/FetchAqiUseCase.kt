package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.jyotirmay.mapapplicationdemo.data.repository.AqiRepository
import com.jyotirmay.mapapplicationdemo.di.IoDispatcher
import com.jyotirmay.mapapplicationdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchAqiUseCase @Inject constructor(
    private val aqiRepository: AqiRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(lat: Double, lng: Double): Flow<Resource<Int>> = flow {
        emit(Resource.Loading)
        try {
            val aqi = withContext(ioDispatcher) {
                aqiRepository.getAqi(lat, lng)
            }
            if (aqi != null) {
                emit(Resource.Success(aqi))
            } else {
                emit(Resource.Error("AQI unavailable"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch AQI"))
        }
    }
}

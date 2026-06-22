package com.jyotirmay.mapapplicationdemo.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.domain.model.BookingStep
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.domain.model.PointKey
import com.jyotirmay.mapapplicationdemo.domain.usecase.BookTripUseCase
import com.jyotirmay.mapapplicationdemo.domain.usecase.FetchAqiUseCase
import com.jyotirmay.mapapplicationdemo.domain.usecase.GetCurrentLocationUseCase
import com.jyotirmay.mapapplicationdemo.domain.usecase.ResolveLocationPointUseCase
import com.jyotirmay.mapapplicationdemo.domain.usecase.UpdateLocationNicknameUseCase
import com.jyotirmay.mapapplicationdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val fetchAqiUseCase: FetchAqiUseCase,
    private val resolveLocationPointUseCase: ResolveLocationPointUseCase,
    private val updateLocationNicknameUseCase: UpdateLocationNicknameUseCase,
    private val bookTripUseCase: BookTripUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private var aqiJob: Job? = null

    fun onLocationPermissionGranted() {
        _uiState.update { it.copy(hasLocationPermission = true) }
        viewModelScope.launch {
            val location = getCurrentLocationUseCase()
            _uiState.update { it.copy(initialCameraPosition = location) }
            fetchAqiForCenter(location.latitude, location.longitude)
        }
    }

    fun onMapCenterChanged(latLng: LatLng) {
        fetchAqiForCenter(latLng.latitude, latLng.longitude)
    }

    fun onVButtonClick(latLng: LatLng) {
        when (_uiState.value.bookingStep) {
            BookingStep.SetA, BookingStep.SetB -> setBookingPoint(latLng)
            BookingStep.Book -> bookTrip()
        }
    }

    fun getPointForKey(pointKey: PointKey): LocationPoint? = when (pointKey) {
        PointKey.A -> _uiState.value.pointA
        PointKey.B -> _uiState.value.pointB
    }

    fun getBookingResult(): BookingResult? = _uiState.value.bookingResult

    fun resetBooking() {
        _uiState.update { state ->
            state.copy(
                pointA = null,
                pointB = null,
                bookingStep = BookingStep.SetA,
                bookingResult = null,
                isBooking = false,
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun onRecenterToCurrentLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRecentering = true, errorMessage = null) }
            try {
                val location = getCurrentLocationUseCase()
                _uiState.update {
                    it.copy(
                        isRecentering = false,
                        recenterTarget = location,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isRecentering = false,
                        errorMessage = e.message ?: "Failed to get current location",
                    )
                }
            }
        }
    }

    fun onRecenterHandled() {
        _uiState.update { it.copy(recenterTarget = null) }
    }

    fun onNicknameConfirmed(pointKey: PointKey, nickname: String) {
        val current = getPointForKey(pointKey) ?: return
        val updated = updateLocationNicknameUseCase(current, nickname)
        _uiState.update { state ->
            when (pointKey) {
                PointKey.A -> state.copy(pointA = updated)
                PointKey.B -> state.copy(pointB = updated)
            }
        }
    }

    private fun fetchAqiForCenter(lat: Double, lng: Double) {
        aqiJob?.cancel()
        aqiJob = viewModelScope.launch {
            fetchAqiUseCase(lat, lng).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoadingAqi = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoadingAqi = false,
                                currentAqi = resource.data,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoadingAqi = false) }
                    }
                }
            }
        }
    }

    private fun setBookingPoint(latLng: LatLng) {
        viewModelScope.launch {
            resolveLocationPointUseCase(latLng.latitude, latLng.longitude).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isSettingPoint = true, errorMessage = null) }
                    }

                    is Resource.Success -> {
                        when (_uiState.value.bookingStep) {
                            BookingStep.SetA -> {
                                _uiState.update {
                                    it.copy(
                                        isSettingPoint = false,
                                        pointA = resource.data,
                                        bookingStep = BookingStep.SetB,
                                    )
                                }
                            }

                            BookingStep.SetB -> {
                                _uiState.update {
                                    it.copy(
                                        isSettingPoint = false,
                                        pointB = resource.data,
                                        bookingStep = BookingStep.Book,
                                    )
                                }
                            }

                            BookingStep.Book -> Unit
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isSettingPoint = false,
                                errorMessage = resource.message,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun bookTrip() {
        val pointA = _uiState.value.pointA ?: return
        val pointB = _uiState.value.pointB ?: return
        viewModelScope.launch {
            bookTripUseCase(pointA, pointB).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isBooking = true, errorMessage = null) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isBooking = false,
                                bookingResult = resource.data,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isBooking = false,
                                errorMessage = resource.message,
                            )
                        }
                    }
                }
            }
        }
    }
}

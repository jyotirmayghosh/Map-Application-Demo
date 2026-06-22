package com.jyotirmay.mapapplicationdemo.data.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceLocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): LatLng {
        val cancellationTokenSource = CancellationTokenSource()
        val location = fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token,
        ).await()
        return LatLng(location.latitude, location.longitude)
    }
}

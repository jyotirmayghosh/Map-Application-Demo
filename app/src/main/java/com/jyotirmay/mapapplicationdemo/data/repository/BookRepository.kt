package com.jyotirmay.mapapplicationdemo.data.repository

import com.jyotirmay.mapapplicationdemo.data.remote.BookApiService
import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookLocationDto
import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookRequestDto
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val bookApiService: BookApiService,
) {

    suspend fun getBooks(year: Int, month: Int): List<BookingResult> =
        bookApiService.getBooks(year, month).map { response ->
            BookingResult(
                id = response.id,
                pointA = response.a.toBookedLocation(nickname = null),
                pointB = response.b.toBookedLocation(nickname = null),
                price = response.price,
            )
        }

    suspend fun createBook(pointA: LocationPoint, pointB: LocationPoint): BookingResult {
        val request = BookRequestDto(
            a = pointA.toBookLocationDto(),
            b = pointB.toBookLocationDto(),
        )
        val response = bookApiService.createBook(request)
        return BookingResult(
            id = response.id,
            pointA = response.a.toBookedLocation(pointA.nickname),
            pointB = response.b.toBookedLocation(pointB.nickname),
            price = response.price,
        )
    }

    private fun LocationPoint.toBookLocationDto(): BookLocationDto =
        BookLocationDto(
            latitude = lat,
            longitude = lng,
            aqi = aqi,
            name = addressName,
        )

    private fun BookLocationDto.toBookedLocation(nickname: String?): BookedLocation =
        BookedLocation(
            latitude = latitude,
            longitude = longitude,
            aqi = aqi,
            name = name,
            nickname = nickname,
        )
}

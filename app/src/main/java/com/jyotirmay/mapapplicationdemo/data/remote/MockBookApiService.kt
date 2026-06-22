package com.jyotirmay.mapapplicationdemo.data.remote

import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookLocationDto
import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookRequestDto
import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookResponseDto

class MockBookApiService : BookApiService {

    override suspend fun getBooks(year: Int, month: Int): List<BookResponseDto> = MOCK_HISTORY

    override suspend fun createBook(request: BookRequestDto): BookResponseDto =
        BookResponseDto(
            a = request.a,
            b = request.b,
            price = MOCK_PRICE,
            id = "book-${System.currentTimeMillis()}",
        )

    private companion object {
        const val MOCK_PRICE = 10000.0

        val MOCK_HISTORY = listOf(
            BookResponseDto(
                a = BookLocationDto(
                    latitude = 36.564,
                    longitude = 127.001,
                    aqi = 30,
                    name = "Seoul A Location",
                ),
                b = BookLocationDto(
                    latitude = 36.567,
                    longitude = 127.0,
                    aqi = 40,
                    name = "Seoul B Location",
                ),
                price = 10000.0,
            ),
            BookResponseDto(
                a = BookLocationDto(
                    latitude = 36.577,
                    longitude = 127.033,
                    aqi = 50,
                    name = "Seoul C Location",
                ),
                b = BookLocationDto(
                    latitude = 36.567,
                    longitude = 127.0,
                    aqi = 60,
                    name = "Seoul D Location",
                ),
                price = 20000.0,
            ),
        )
    }
}

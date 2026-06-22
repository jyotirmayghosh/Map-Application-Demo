package com.jyotirmay.mapapplicationdemo.data.remote

import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookRequestDto
import com.jyotirmay.mapapplicationdemo.data.remote.dto.BookResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookApiService {

    @GET("books")
    suspend fun getBooks(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): List<BookResponseDto>

    @POST("books")
    suspend fun createBook(@Body request: BookRequestDto): BookResponseDto
}

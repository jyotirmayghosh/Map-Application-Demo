package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.jyotirmay.mapapplicationdemo.data.repository.BookRepository
import com.jyotirmay.mapapplicationdemo.di.IoDispatcher
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookTripUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(pointA: LocationPoint, pointB: LocationPoint): Flow<Resource<BookingResult>> = flow {
        emit(Resource.Loading)
        try {
            val result = withContext(ioDispatcher) {
                bookRepository.createBook(pointA, pointB)
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to create booking"))
        }
    }
}

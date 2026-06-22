package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.jyotirmay.mapapplicationdemo.data.repository.BookRepository
import com.jyotirmay.mapapplicationdemo.di.IoDispatcher
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBookHistoryUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(year: Int, month: Int): Flow<Resource<List<BookingResult>>> = flow {
        emit(Resource.Loading)
        try {
            val result = withContext(ioDispatcher) {
                bookRepository.getBooks(year, month)
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load booking history"))
        }
    }
}

package com.jyotirmay.mapapplicationdemo.ui.history

import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult

data class UsageHistoryUiState(
    val isLoading: Boolean = true,
    val items: List<BookingResult> = emptyList(),
    val totalCount: Int = 0,
    val totalPrice: Double = 0.0,
    val errorMessage: String? = null,
)

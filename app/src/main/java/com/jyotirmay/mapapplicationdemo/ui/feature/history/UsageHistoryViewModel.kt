package com.jyotirmay.mapapplicationdemo.ui.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyotirmay.mapapplicationdemo.domain.usecase.GetBookHistoryUseCase
import com.jyotirmay.mapapplicationdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class UsageHistoryViewModel @Inject constructor(
    private val getBookHistoryUseCase: GetBookHistoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageHistoryUiState())
    val uiState: StateFlow<UsageHistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        val now = YearMonth.now()
        viewModelScope.launch {
            getBookHistoryUseCase(now.year, now.monthValue).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {
                        val items = resource.data
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                items = items,
                                totalCount = items.size,
                                totalPrice = items.sumOf { booking -> booking.price },
                                errorMessage = null,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.message,
                            )
                        }
                    }
                }
            }
        }
    }
}

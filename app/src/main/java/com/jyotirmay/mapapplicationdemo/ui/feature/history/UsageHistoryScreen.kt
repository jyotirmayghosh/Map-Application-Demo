package com.jyotirmay.mapapplicationdemo.ui.feature.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.ui.components.TextSize
import com.jyotirmay.mapapplicationdemo.ui.components.TextView
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

@Composable
fun UsageHistoryScreen(
    uiState: UsageHistoryUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    BackHandler(onBack = onBack)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        }

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    TextView(
                        text = uiState.errorMessage,
                        size = TextSize.BodyLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                    TextButton(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }

            else -> {
                SummaryHeader(
                    totalCount = uiState.totalCount,
                    totalPrice = uiState.totalPrice,
                    modifier = Modifier.padding(top = 8.dp),
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    items(uiState.items) { booking ->
                        HistoryListItem(booking = booking)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryHeader(
    totalCount: Int,
    totalPrice: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        SummaryColumn(
            label = "Total Count",
            value = totalCount.toString(),
            modifier = Modifier.weight(1f),
        )
        SummaryColumn(
            label = "Total Price",
            value = formatPrice(totalPrice),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SummaryColumn(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextView(
            text = label,
            size = TextSize.BodyMedium,
            color = Color.Gray,
        )
        TextView(
            text = value,
            size = TextSize.HeadlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun HistoryListItem(
    booking: BookingResult,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LocationNameRow(label = "A", name = booking.pointA.name)
        LocationNameRow(label = "B", name = booking.pointB.name)
    }
}

@Composable
private fun LocationNameRow(
    label: String,
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextView(
            text = label,
            size = TextSize.TitleMedium,
            fontWeight = FontWeight.Bold,
        )
        TextView(
            text = name,
            size = TextSize.BodyLarge,
            modifier = Modifier.weight(1f),
        )
    }
}

private fun formatPrice(price: Double): String =
    if (price % 1.0 == 0.0) price.toLong().toString() else price.toString()

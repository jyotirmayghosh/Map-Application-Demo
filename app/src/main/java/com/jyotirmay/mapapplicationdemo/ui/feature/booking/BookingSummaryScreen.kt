package com.jyotirmay.mapapplicationdemo.ui.feature.booking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.ui.components.AppButton
import com.jyotirmay.mapapplicationdemo.ui.components.TextSize
import com.jyotirmay.mapapplicationdemo.ui.components.TextView
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

@Composable
fun BookingSummaryScreen(
    bookingResult: BookingResult,
    onContinue: () -> Unit,
    onBack: () -> Unit,
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

        LocationSection(
            label = "A",
            location = bookingResult.pointA,
            modifier = Modifier.padding(top = 8.dp),
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        LocationSection(
            label = "B",
            location = bookingResult.pointB,
        )

        Spacer(modifier = Modifier.weight(1f))

        SummaryRow(
            label = "price",
            value = bookingResult.price.toString(),
            labelSize = TextSize.TitleMedium,
            valueSize = TextSize.TitleMedium,
        )

        AppButton(
            text = "V",
            onClick = onContinue,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
private fun LocationSection(
    label: String,
    location: BookedLocation,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextView(
                text = label,
                size = TextSize.HeadlineMedium,
                fontWeight = FontWeight.Bold,
            )
            TextView(
                text = location.name,
                size = TextSize.TitleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }

        SummaryRow(
            label = "aqi",
            value = location.aqi?.toString() ?: "--",
        )

        SummaryRow(
            label = "nickname",
            value = location.nickname?.takeIf { it.isNotBlank() } ?: "—",
        )
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelSize: TextSize = TextSize.BodySmall,
    valueSize: TextSize = TextSize.BodyLarge,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextView(
            text = label,
            size = labelSize,
            color = Color.Gray,
        )
        TextView(
            text = value,
            size = valueSize,
            fontWeight = FontWeight.Bold,
        )
    }
}

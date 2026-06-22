package com.jyotirmay.mapapplicationdemo.ui.booking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.domain.model.BookedLocation
import com.jyotirmay.mapapplicationdemo.domain.model.BookingResult
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

private val YellowButton = Color(0xFFFFD600)

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
            labelStyle = MaterialTheme.typography.titleMedium,
            valueStyle = MaterialTheme.typography.titleMedium,
        )

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = YellowButton,
                contentColor = Color.Black,
            ),
        ) {
            Text(
                text = "V",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
        }
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
            Text(
                text = label,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleMedium,
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
    labelStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodySmall,
    valueStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = labelStyle,
            color = Color.Gray,
        )
        Text(
            text = value,
            style = valueStyle,
            fontWeight = FontWeight.Bold,
        )
    }
}

private val previewBookingResult = BookingResult(
    id = "book-preview",
    pointA = BookedLocation(
        latitude = 36.564,
        longitude = 127.001,
        aqi = 30,
        name = "Seoul A Location",
        nickname = "home",
    ),
    pointB = BookedLocation(
        latitude = 36.567,
        longitude = 127.0,
        aqi = 40,
        name = "Seoul B Location",
        nickname = "office",
    ),
    price = 10000.0,
)

@Preview(showBackground = true, name = "Booking Summary")
@Composable
private fun BookingSummaryScreenPreview() {
    MapApplicationDemoTheme {
        BookingSummaryScreen(
            bookingResult = previewBookingResult,
            onContinue = {},
            onBack = {},
        )
    }
}

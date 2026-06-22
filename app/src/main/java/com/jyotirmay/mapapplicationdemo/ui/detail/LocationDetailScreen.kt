package com.jyotirmay.mapapplicationdemo.ui.detail

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import com.jyotirmay.mapapplicationdemo.domain.model.PointKey
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

private val YellowButton = Color(0xFFFFD600)
private const val MAX_NICKNAME_LENGTH = 20

@Composable
fun LocationDetailScreen(
    pointKey: PointKey,
    locationPoint: LocationPoint?,
    onConfirm: (nickname: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (locationPoint == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            BackButton(onBack = onBack)
            Text(
                text = "Location not found",
                modifier = Modifier.padding(top = 16.dp),
            )
        }
        return
    }

    var nickname by remember(locationPoint) {
        mutableStateOf(locationPoint.nickname.orEmpty())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
    ) {
        BackButton(onBack = onBack)

        LocationHeader(
            pointKey = pointKey,
            addressName = locationPoint.addressName,
            aqi = locationPoint.aqi,
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedTextField(
            value = nickname,
            onValueChange = { value ->
                if (value.length <= MAX_NICKNAME_LENGTH) {
                    nickname = value
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "nickname") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
        )

        Button(
            onClick = { onConfirm(nickname) },
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
                text = "Save",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
private fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onBack, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
        )
    }
}

@Composable
private fun LocationHeader(
    pointKey: PointKey,
    addressName: String,
    aqi: Int?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = pointKey.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = addressName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "aqi",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
            )
            Text(
                text = aqi?.toString() ?: "--",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private val previewLocationPoint = LocationPoint(
    lat = -34.9285,
    lng = 138.6007,
    addressName = "Adelaide, South Australia",
    aqi = 80,
)

@Preview(showBackground = true, name = "Location A")
@Composable
private fun LocationDetailScreenPreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.A,
            locationPoint = previewLocationPoint,
            onConfirm = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Location B with nickname")
@Composable
private fun LocationDetailScreenWithNicknamePreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.B,
            locationPoint = previewLocationPoint.copy(nickname = "Home"),
            onConfirm = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Not Found")
@Composable
private fun LocationDetailScreenEmptyPreview() {
    MapApplicationDemoTheme {
        LocationDetailScreen(
            pointKey = PointKey.B,
            locationPoint = null,
            onConfirm = {},
            onBack = {},
        )
    }
}

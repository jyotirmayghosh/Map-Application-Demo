package com.jyotirmay.mapapplicationdemo.ui.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.jyotirmay.mapapplicationdemo.ui.components.AppButton
import com.jyotirmay.mapapplicationdemo.ui.components.EditText
import com.jyotirmay.mapapplicationdemo.ui.components.TextSize
import com.jyotirmay.mapapplicationdemo.ui.components.TextView
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

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
            TextView(
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

        EditText(
            value = nickname,
            onValueChange = { nickname = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = "nickname",
            maxLength = MAX_NICKNAME_LENGTH,
        )

        AppButton(
            text = "Save",
            onClick = { onConfirm(nickname) },
            modifier = Modifier.padding(top = 16.dp),
        )
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
            TextView(
                text = pointKey.name,
                size = TextSize.HeadlineMedium,
                fontWeight = FontWeight.Bold,
            )
            TextView(
                text = addressName,
                size = TextSize.TitleMedium,
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
            TextView(
                text = "aqi",
                size = TextSize.BodySmall,
                color = Color.Gray,
            )
            TextView(
                text = aqi?.toString() ?: "--",
                size = TextSize.HeadlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

package com.jyotirmay.mapapplicationdemo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme
import com.jyotirmay.mapapplicationdemo.ui.theme.YellowButton

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textSize: TextSize = TextSize.TitleLarge,
    fillMaxWidth: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = if (fillMaxWidth) modifier.fillMaxWidth() else modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = YellowButton,
            contentColor = Color.Black,
        ),
    ) {
        TextView(
            text = text,
            size = textSize,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true, name = "AppButton full width")
@Composable
private fun AppButtonFullWidthPreview() {
    MapApplicationDemoTheme {
        AppButton(
            text = "Save",
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, name = "AppButton compact")
@Composable
private fun AppButtonCompactPreview() {
    MapApplicationDemoTheme {
        AppButton(
            text = "Set A",
            onClick = {},
            fillMaxWidth = false,
            textSize = TextSize.BodySmall,
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp),
        )
    }
}

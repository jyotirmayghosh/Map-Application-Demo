package com.jyotirmay.mapapplicationdemo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

enum class TextSize {
    HeadlineLarge,
    HeadlineMedium,
    HeadlineSmall,
    TitleLarge,
    TitleMedium,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
}

@Composable
fun TextView(
    text: String,
    modifier: Modifier = Modifier,
    size: TextSize = TextSize.BodyMedium,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        style = size.toTextStyle(),
        color = color,
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
    )
}

@Composable
private fun TextSize.toTextStyle(): TextStyle {
    val typography = MaterialTheme.typography
    return when (this) {
        TextSize.HeadlineLarge -> typography.headlineLarge
        TextSize.HeadlineMedium -> typography.headlineMedium
        TextSize.HeadlineSmall -> typography.headlineSmall
        TextSize.TitleLarge -> typography.titleLarge
        TextSize.TitleMedium -> typography.titleMedium
        TextSize.BodyLarge -> typography.bodyLarge
        TextSize.BodyMedium -> typography.bodyMedium
        TextSize.BodySmall -> typography.bodySmall
        TextSize.LabelLarge -> typography.labelLarge
    }
}

@Preview(showBackground = true, name = "TextView sizes")
@Composable
private fun TextViewPreview() {
    MapApplicationDemoTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextView(text = "Headline Medium", size = TextSize.HeadlineMedium, fontWeight = FontWeight.Bold)
            TextView(text = "Title Medium", size = TextSize.TitleMedium)
            TextView(text = "Body Medium", size = TextSize.BodyMedium)
            TextView(text = "Body Small", size = TextSize.BodySmall, color = Color.Gray)
        }
    }
}

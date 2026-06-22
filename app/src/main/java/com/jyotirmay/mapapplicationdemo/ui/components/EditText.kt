package com.jyotirmay.mapapplicationdemo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme

@Composable
fun EditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLength: Int? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (maxLength == null || newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        placeholder = {
            if (placeholder.isNotEmpty()) {
                TextView(text = placeholder, size = TextSize.BodyMedium)
            }
        },
        singleLine = singleLine,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
    )
}

@Preview(showBackground = true, name = "EditText")
@Composable
private fun EditTextPreview() {
    MapApplicationDemoTheme {
        EditText(
            value = "",
            onValueChange = {},
            placeholder = "nickname",
            maxLength = 20,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

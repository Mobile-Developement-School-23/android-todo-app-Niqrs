package com.niqr.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


/**
 * App Typography
 */
val ExtendedAppTypography = ExtendedTypography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 32.sp
    ),
    title = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 24.sp
    ),
    body = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    subhead = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

@Preview
@Composable
private fun TypographyPreview() {
    TodoAppTheme {
        Column(
            modifier = Modifier
                .background(White)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            with(ExtendedTheme.typography) {
                TextStylePreview(titleLarge, "Large title")
                TextStylePreview(title, "Title")
                TextStylePreview(titleSmall, "Small title")
                TextStylePreview(button, "BUTTON")
                TextStylePreview(body, "Body")
                TextStylePreview(subhead, "subhead")
            }
        }
    }
}

@Composable
private fun TextStylePreview(
    style: TextStyle,
    name: String
) {
    Text(
        text = "$name — ${style.fontSize.value.roundToInt()}/${style.lineHeight.value.roundToInt()}",
        style = style
    )
}
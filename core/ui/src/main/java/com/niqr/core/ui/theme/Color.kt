package com.niqr.core.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * App color palette
 */
val Red = Color(0xFFFF3B30)
val Green = Color(0xFF34C759)
val Blue = Color(0xFF007AFF)
val BlueTranslucent = Color(0x4D007AFF)
val Gray = Color(0xFF8E8E93)
val GrayLight = Color(0xFFD1D1D6)
val White = Color(0xFFFFFFFF)

val LightSupportSeparator = Color(0x33000000)
val LightSupportOverlay = Color(0x0F000000)
val LightLabelPrimary = Color(0xFF000000)
val LightLabelSecondary = Color(0x99000000)
val LightLabelTertiary = Color(0x4D000000)
val LightLabelDisable = Color(0x26000000)
val LightBackPrimary = Color(0xFFF7F6F2)
val LightBackSecondary = Color(0xFFFFFFFF)
val LightBackElevated = Color(0xFFFFFFFF)

val DarkSupportSeparator = Color(0x33FFFFFF)
val DarkSupportOverlay = Color(0x52000000)
val DarkLabelPrimary = Color(0xFFFFFFFF)
val DarkLabelSecondary = Color(0x99FFFFFF)
val DarkLabelTertiary = Color(0x66FFFFFF)
val DarkLabelDisable = Color(0x26FFFFFF)
val DarkBackPrimary = Color(0xFF161618)
val DarkBackSecondary = Color(0xFF252528)
val DarkBackElevated = Color(0xFF3C3C3F)

@Preview(
    name = "Color Palette Light",
    widthDp = ColorWidth * 4,
    heightDp = ColorHeight * 3,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Color Palette Light",
    widthDp = ColorWidth * 4,
    heightDp = ColorHeight * 3,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ColorPalettePreview() {
    TodoAppTheme {
        with(ExtendedTheme.colors) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFE5E5E5)) // Figma background color
            ) {
                Row {
                    ColorPreview(supportSeparator, "Support / Separator")
                    ColorPreview(supportOverlay, "Support / Overlay")
                }
                Row {
                    ColorPreview(labelPrimary, "Label / Primary")
                    ColorPreview(labelSecondary, "Label / Secondary")
                    ColorPreview(labelTertiary, "Label / Tertiary")
                    ColorPreview(labelDisable, "Label / Disable")
                }
                Row {
                    ColorPreview(backPrimary, "Back / Primary")
                    ColorPreview(backSecondary, "Back / Secondary")
                    ColorPreview(backElevated, "Back / Elevated")
                }
            }
        }
    }
}

@Preview(
    name = "Stable Color Palette",
    widthDp = ColorWidth * 6,
    heightDp = ColorHeight * 1,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun StableColorPalettePreview() {
    TodoAppTheme {
        Row {
            ColorPreview(Red, "Color / Red")
            ColorPreview(Green, "Color / Red")
            ColorPreview(Blue, "Color / Red")
            ColorPreview(Gray, "Color / Red")
            ColorPreview(GrayLight, "Color / Red")
            ColorPreview(White, "Color / Red")
        }
    }
}

private const val ColorWidth = 288
private const val ColorHeight = 120

@Composable
private fun ColorPreview(color: Color, label: String) {
    val textColor = if (color.isDark()) White else Color.Black
    Box(
        modifier = Modifier
            .size(width = ColorWidth.dp, height = ColorHeight.dp)
            .background(color)
            .padding(12.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column {
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    color = textColor
                )
            ) {
                Text(text = label)
                Text(text = color.toHexCode())
            }
        }
    }
}


private fun Color.toHexCode(): String {
    val alpha = this.alpha * 255
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return when(alpha) {
        255f -> String.format(format = "#%02X%02X%02X", red.toInt(), green.toInt(), blue.toInt())
        else -> String.format(format = "#%02X%02X%02X%02X", alpha.toInt(), red.toInt(), green.toInt(), blue.toInt())
    }
}

private fun Color.isDark(): Boolean {
    val color = toArgb()
    val darkness = (1 - (0.299 * red + 0.587 * green + 0.114 * blue)) * alpha
    return darkness >= 0.3
}
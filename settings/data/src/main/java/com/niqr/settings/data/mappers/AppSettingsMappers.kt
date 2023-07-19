package com.niqr.settings.data.mappers

import com.niqr.settings.data.model.AppSettingsDto
import com.niqr.settings.domain.model.AppSettings

fun AppSettings.toDto() = AppSettingsDto(
    theme = theme
)

fun AppSettingsDto.toModel() = AppSettings(
    theme = theme
)
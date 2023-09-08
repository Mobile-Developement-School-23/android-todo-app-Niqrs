package com.niqr.settings.data.model

import com.niqr.settings.domain.model.Theme
import kotlinx.serialization.Serializable

@Serializable
data class AppSettingsDto(
    val theme: Theme = Theme.SYSTEM
)
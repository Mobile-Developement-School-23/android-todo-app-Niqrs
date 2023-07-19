package com.niqr.settings.domain.settings

import com.niqr.settings.domain.model.AppSettings
import com.niqr.settings.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface AppSettingsProvider {
    fun settingsFlow(): Flow<AppSettings>
    fun settings(): AppSettings
}

interface AppSettingsMutableProvider: AppSettingsProvider {
    suspend fun updateTheme(theme: Theme)
    suspend fun resetAll()
}
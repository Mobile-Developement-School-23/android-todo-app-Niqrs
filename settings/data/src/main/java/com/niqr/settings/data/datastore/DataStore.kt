package com.niqr.settings.data.datastore

import android.content.Context
import androidx.datastore.dataStore

/**
 * Settings DataStore
 */
val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer())

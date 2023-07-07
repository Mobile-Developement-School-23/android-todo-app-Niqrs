package com.niqr.auth.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * Authentication DataStore
 */
internal val Context.dataStore by preferencesDataStore("auth")

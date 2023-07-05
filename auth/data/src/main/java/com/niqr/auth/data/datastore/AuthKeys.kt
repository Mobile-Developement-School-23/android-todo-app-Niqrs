package com.niqr.auth.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

internal object AuthKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth")
    val REVISION = stringPreferencesKey("revision")
    val DEVICE_ID = stringPreferencesKey("device_id")
}

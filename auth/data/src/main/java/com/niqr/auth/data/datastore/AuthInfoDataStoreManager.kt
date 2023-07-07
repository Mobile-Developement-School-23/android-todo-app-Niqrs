package com.niqr.auth.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.niqr.auth.data.di.AuthProviderScope
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AuthProviderScope
class AuthInfoDataStoreManager @Inject constructor(
    context: Context
): AuthInfoMutableProvider {
    private val dataStore = context.dataStore

    override fun authInfoFlow(): Flow<AuthInfo> = dataStore.data.map { preferences ->
        val token = preferences[AuthKeys.AUTH_TOKEN]
        val revision = preferences[AuthKeys.REVISION]
        val deviceId = preferences[AuthKeys.DEVICE_ID]
        AuthInfo(
            token = token ?: "",
            revision = revision ?: "0",
            deviceId = deviceId ?: ""
        )
    }
    override fun authInfo(): AuthInfo = runBlocking {
        dataStore.data.first().let { preferences ->
            val token = preferences[AuthKeys.AUTH_TOKEN]
            val revision = preferences[AuthKeys.REVISION]
            val deviceId = preferences[AuthKeys.DEVICE_ID]
            AuthInfo(
                token = token ?: "",
                revision = revision ?: "0",
                deviceId = deviceId ?: ""
            )
        }
    }
    override suspend fun updateAuthInfo(info: AuthInfo) {
        dataStore.edit { preferences ->
            preferences[AuthKeys.AUTH_TOKEN] = info.token
            preferences[AuthKeys.REVISION] = info.revision
            preferences[AuthKeys.DEVICE_ID] = info.deviceId
        }
    }

    override suspend fun updateAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AuthKeys.AUTH_TOKEN] = token
        }
    }

    override suspend fun updateRevision(revision: String) {
        dataStore.edit { preferences ->
            preferences[AuthKeys.REVISION] = revision
        }
    }

    override suspend fun clearAuthInfo() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

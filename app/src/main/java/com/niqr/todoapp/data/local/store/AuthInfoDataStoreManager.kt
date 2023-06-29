package com.niqr.todoapp.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.niqr.todoapp.data.abstraction.AuthInfoMutableProvider
import com.niqr.todoapp.data.model.AuthInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("auth")

@Singleton
class AuthInfoDataStoreManager @Inject constructor(
    @ApplicationContext context: Context
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

private object AuthKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth")
    val REVISION = stringPreferencesKey("revision")
    val DEVICE_ID = stringPreferencesKey("device_id")
}
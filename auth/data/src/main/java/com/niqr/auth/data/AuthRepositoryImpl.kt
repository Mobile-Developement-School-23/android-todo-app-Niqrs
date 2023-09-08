package com.niqr.auth.data

import com.niqr.auth.data.di.AuthProviderScope
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.AuthRepository
import com.niqr.auth.domain.model.AuthInfo
import com.niqr.settings.domain.settings.AppSettingsMutableProvider
import com.yandex.metrica.YandexMetrica
import java.util.UUID
import javax.inject.Inject

/**
 * Controls user authentication state
 */
@AuthProviderScope
class AuthRepositoryImpl @Inject constructor(
    private val authProvider: AuthInfoMutableProvider,
    private val settingsProvider: AppSettingsMutableProvider
): AuthRepository {
    override suspend fun signIn(token: String) {
        authProvider.updateAuthInfo(
            AuthInfo(
                token = token,
                revision = "0",
                deviceId = UUID.randomUUID().toString()
            )
        )
        YandexMetrica.reportEvent("Sign in")
    }

    override suspend fun signOut() {
        authProvider.clearAuthInfo()
        settingsProvider.resetAll()
        YandexMetrica.reportEvent("Sign out")
    }
}

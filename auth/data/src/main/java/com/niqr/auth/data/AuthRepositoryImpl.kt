package com.niqr.auth.data

import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.AuthRepository
import com.niqr.auth.domain.model.AuthInfo
import java.util.UUID
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authProvider: AuthInfoMutableProvider
): AuthRepository {
    override suspend fun signIn(token: String) {
        authProvider.updateAuthInfo(
            AuthInfo(
                token = token,
                revision = "0",
                deviceId = UUID.randomUUID().toString()
            )
        )
    }

    override suspend fun signOut() {
        authProvider.clearAuthInfo()
    }
}

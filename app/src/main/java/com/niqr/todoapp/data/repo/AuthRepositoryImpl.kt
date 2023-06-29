package com.niqr.todoapp.data.repo

import com.niqr.todoapp.data.abstraction.AuthInfoMutableProvider
import com.niqr.todoapp.data.abstraction.AuthRepository
import com.niqr.todoapp.data.model.AuthInfo
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
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
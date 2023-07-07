package com.niqr.auth.domain

import com.niqr.auth.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

/**
 * Immutable abstraction for providing authentication info
 */
interface AuthInfoProvider {
    fun authInfoFlow(): Flow<AuthInfo>
    fun authInfo(): AuthInfo

}

/**
 * Mutable abstraction for controlling authentication info
 */
interface AuthInfoMutableProvider: AuthInfoProvider {
    suspend fun updateAuthInfo(info: AuthInfo)
    suspend fun updateAuthToken(token: String)
    suspend fun updateRevision(revision: String)
    suspend fun clearAuthInfo()
}

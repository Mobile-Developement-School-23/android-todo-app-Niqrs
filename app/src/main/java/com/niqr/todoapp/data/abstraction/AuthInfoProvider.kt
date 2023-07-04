package com.niqr.todoapp.data.abstraction

import com.niqr.todoapp.data.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface AuthInfoProvider {
    fun authInfoFlow(): Flow<AuthInfo>
    fun authInfo(): AuthInfo

}

interface AuthInfoMutableProvider: AuthInfoProvider {
    suspend fun updateAuthInfo(info: AuthInfo)
    suspend fun updateAuthToken(token: String)
    suspend fun updateRevision(revision: String)
    suspend fun clearAuthInfo()
}
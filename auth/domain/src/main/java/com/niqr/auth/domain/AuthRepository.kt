package com.niqr.auth.domain

interface AuthRepository {
    suspend fun signIn(token: String)
    suspend fun signOut()
}

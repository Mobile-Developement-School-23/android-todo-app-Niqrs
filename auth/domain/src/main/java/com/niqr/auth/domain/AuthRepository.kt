package com.niqr.auth.domain

/**
 * Authentication repository abstraction
 */
interface AuthRepository {
    suspend fun signIn(token: String)
    suspend fun signOut()
}

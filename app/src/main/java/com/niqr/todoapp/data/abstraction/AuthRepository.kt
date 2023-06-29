package com.niqr.todoapp.data.abstraction

interface AuthRepository {
    suspend fun signIn(token: String)
    suspend fun signOut()
}
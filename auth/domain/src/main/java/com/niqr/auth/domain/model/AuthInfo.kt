package com.niqr.auth.domain.model

import java.util.UUID

/**
 * Model for authentication information
 */
data class AuthInfo(
    val token: String = "",
    val revision: String = "0",
    val deviceId: String = UUID.randomUUID().toString(),
)

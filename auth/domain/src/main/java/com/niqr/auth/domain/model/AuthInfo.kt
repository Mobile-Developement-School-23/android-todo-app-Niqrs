package com.niqr.auth.domain.model

import java.util.UUID

data class AuthInfo(
    val token: String = "",
    val revision: String = "0",
    val deviceId: String = UUID.randomUUID().toString(),
)

package com.niqr.auth.ui.model

/**
 * Contains info about auth ui actions
 */
sealed class AuthAction {
    object AuthClick: AuthAction()
    data class AuthResult(val token: String?): AuthAction()
}

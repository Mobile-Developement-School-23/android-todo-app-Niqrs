package com.niqr.auth.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val AuthScreenRoutePattern = "auth"

fun NavController.navigateToAuth() {
    this.navigate((AuthScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

fun NavGraphBuilder.authScreen(
    onSuccessAuth: () -> Unit
) {
    composable(AuthScreenRoutePattern) {
        val viewModel: AuthViewModel = hiltViewModel()
        AuthScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onSuccess = onSuccessAuth
        )
    }
}

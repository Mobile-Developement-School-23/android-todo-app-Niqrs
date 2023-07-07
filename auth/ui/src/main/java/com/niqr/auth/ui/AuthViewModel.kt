package com.niqr.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niqr.auth.domain.AuthRepository
import com.niqr.auth.ui.model.AuthAction
import com.niqr.auth.ui.model.AuthEvent
import com.niqr.auth.ui.model.AuthUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    private val _uiEvent = Channel<AuthEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: AuthAction) {
        when(action) {
            AuthAction.AuthClick -> launchAuthentication()
            is AuthAction.AuthResult -> precessAuthResult(action.token)
        }
    }

    private fun launchAuthentication() {
        viewModelScope.launch {
            _uiEvent.send(AuthEvent.LaunchAuth)
        }
    }

    private fun precessAuthResult(token: String?) {
        viewModelScope.launch {
            if (token != null) {
                repository.signIn(token)
                _uiEvent.send(AuthEvent.AuthSuccess)
            }
        }
    }
}

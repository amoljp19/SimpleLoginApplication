package com.softaai.simpleloginapplication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softaai.simpleloginapplication.login.utils.LoginState
import com.softaai.simpleloginapplication.login.utils.LoginValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel  : ViewModel() {

    private val _loginStateFlow = MutableStateFlow<LoginState>(LoginState.InValidPasswordState)
    val loginStateFlow : StateFlow<LoginState>
           get() = _loginStateFlow

    fun doLogin(username: String, password: String) {

        if (!LoginValidator.isUsernameValid(username)) {
            _loginStateFlow.value = LoginState.InValidUsernameState
        } else if (!LoginValidator.isPasswordValid(password)) {
            _loginStateFlow.value = LoginState.InValidPasswordState
        } else {
            _loginStateFlow.value = LoginState.ValidCredentialsState
        }
    }
}
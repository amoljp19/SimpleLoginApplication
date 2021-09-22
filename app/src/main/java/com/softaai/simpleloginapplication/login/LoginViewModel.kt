package com.softaai.simpleloginapplication.login

import androidx.lifecycle.ViewModel
import com.softaai.simpleloginapplication.login.utils.LoginState
import com.softaai.simpleloginapplication.login.utils.LoginValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _loginStateFlow = MutableStateFlow<LoginState>(LoginState.LoginDisabled)
    val loginStateFlow: StateFlow<LoginState>
        get() = _loginStateFlow


    fun enableLogin(username: String, password: String) {
        if ((!username.isEmpty() && !password.isEmpty()) && isValidUser(username, password)) {
            _loginStateFlow.value = LoginState.LoginEnabled
        } else {
            _loginStateFlow.value = LoginState.LoginDisabled
        }
    }

    fun doLogin(username: String, password: String) {
        if (isValidUser(username, password)) {
            _loginStateFlow.value = LoginState.LoginSuccess
        }
    }

    fun isValidUser(username: String, password: String): Boolean {
        return if (!LoginValidator.isUsernameValid(username)) {
            _loginStateFlow.value = LoginState.InValidUsernameState
            false
        } else if (!LoginValidator.isPasswordValid(password)) {
            _loginStateFlow.value = LoginState.InValidPasswordState
            false
        } else {
            _loginStateFlow.value = LoginState.ValidCredentialsState
            true
        }
    }
}
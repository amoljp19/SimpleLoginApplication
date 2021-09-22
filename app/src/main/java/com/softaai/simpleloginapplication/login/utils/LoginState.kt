package com.softaai.simpleloginapplication.login.utils

sealed class LoginState {
    object LoginEnabled : LoginState()
    object LoginDisabled : LoginState()
    object LoginSuccess : LoginState()
    object ValidCredentialsState : LoginState()
    object InValidUsernameState : LoginState()
    object InValidPasswordState : LoginState()
}

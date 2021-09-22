package com.softaai.simpleloginapplication.login.utils

import com.softaai.simpleloginapplication.login.model.LoginUser

sealed class LoginState {
    data class Error(val message: String?) : LoginState()
    object ValidCredentialsState : LoginState()
    object InValidEmailState : LoginState()
    object InValidPasswordState : LoginState()
    data class Success(val data: LoginUser?) : LoginState()
}

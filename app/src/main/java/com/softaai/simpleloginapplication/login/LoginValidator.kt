package com.softaai.simpleloginapplication.login

import java.util.regex.Pattern

object LoginValidator {

    private val VALID_USERNAME: Pattern = Pattern.compile(
        "\\S\\w",
        Pattern.CASE_INSENSITIVE
    )

    private val VALID_PASSWORD: Pattern = Pattern.compile("[0-9a-zA-Z@!?_]", Pattern.CASE_INSENSITIVE)

    private const val MIN_PASSWORD_LENGTH = 5

    fun isUsernameValid(username: String): Boolean {
        return if (username.isEmpty()) {
            false
        } else {
            VALID_USERNAME.matcher(username).matches()
        }
    }

    fun isPasswordValid(password: String?): Boolean {
        return if (password == null || password.trim { it <= ' ' }.isEmpty()) {
            false
        } else {
            VALID_PASSWORD.matcher(password).matches() && password.trim { it <= ' ' }.length >= MIN_PASSWORD_LENGTH
        }
    }
}
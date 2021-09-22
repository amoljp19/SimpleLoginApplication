package com.softaai.simpleloginapplication.login.utils

import java.util.regex.Pattern

object LoginValidator {

    private val VALID_USERNAME: Pattern = Pattern.compile(
        "[0-9a-zA-Z]+",
        Pattern.CASE_INSENSITIVE
    )

    private val VALID_PASSWORD: Pattern =
        Pattern.compile("[0-9a-zA-Z@!?_]+", Pattern.CASE_INSENSITIVE)

    private const val MIN_PASSWORD_LENGTH = 5

    fun isUsernameValid(username: String): Boolean {
        return if (username.isEmpty()) {
            false
        } else {
            val t1 = VALID_USERNAME.matcher(username).matches()
            return t1
        }
    }

    fun isPasswordValid(password: String?): Boolean {
        return if (password == null || password.trim { it <= ' ' }.isEmpty()) {
            false
        } else {
            val t2 = VALID_PASSWORD.matcher(password)
                .matches() && password.trim { it <= ' ' }.length >= MIN_PASSWORD_LENGTH
            return t2
        }
    }
}
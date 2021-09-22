package com.softaai.simpleloginapplication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.softaai.simpleloginapplication.MainActivity
import com.softaai.simpleloginapplication.databinding.ActivityLoginBinding
import com.softaai.simpleloginapplication.login.model.LoginUser
import com.softaai.simpleloginapplication.login.utils.LoginState
import kotlinx.coroutines.flow.collect

class LoginActivity : AppCompatActivity() {

    private lateinit var mViewModel: LoginViewModel

    private lateinit var mViewBinding: ActivityLoginBinding

    private lateinit var loginUser: LoginUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        mViewBinding.inputUsername.addTextChangedListener(loginTextWatcher)
        mViewBinding.inputPassword.addTextChangedListener(loginTextWatcher)

        mViewBinding.login.setOnClickListener {
            hideKeyboard(mViewBinding.login)

            mViewModel.doLogin(
                loginUser.username,
                loginUser.password
            )
        }

        observeLoginStates()
    }


    private fun observeLoginStates() {
        lifecycleScope.launchWhenStarted {
            mViewModel.loginStateFlow.collect {
                when (it) {
                    LoginState.LoginDisabled -> {
                        setLoginButtonStatus(enabledStatus = false)
                    }
                    LoginState.LoginEnabled -> {
                        setLoginButtonStatus(enabledStatus = true)
                    }
                    LoginState.InValidUsernameState -> {
                        setUsernameError()
                    }
                    LoginState.InValidPasswordState -> {
                        resetUsernameError()
                        setPasswordError()
                    }
                    LoginState.ValidCredentialsState -> {
                        resetUsernameError()
                        resetPasswordError()
                    }
                    LoginState.LoginSuccess -> {
                        showMainScreen()
                    }
                }
            }
        }
    }

    private fun setLoginButtonStatus(enabledStatus: Boolean) {
        mViewBinding.login.isEnabled = enabledStatus
    }

    private fun setUsernameError() {
        mViewBinding.textInputUsername.error = "Enter proper user name"
        mViewBinding.textInputUsername.isErrorEnabled = true
    }

    private fun setPasswordError() {
        mViewBinding.textInputPassword.error = "Password not meeting the criteria"
        mViewBinding.textInputPassword.isErrorEnabled = true
    }

    private fun resetUsernameError() {
        mViewBinding.textInputUsername.error = null
        mViewBinding.textInputUsername.isErrorEnabled = false
    }

    private fun resetPasswordError() {
        mViewBinding.textInputPassword.error = null
        mViewBinding.textInputPassword.isErrorEnabled = false
    }

    private fun showMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", loginUser.username);
        startActivity(intent)
        finish()
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private val loginTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            loginUser = LoginUser(
                mViewBinding.inputUsername.text.toString(),
                mViewBinding.inputPassword.text.toString()
            )

            mViewModel.enableLogin(loginUser.username, loginUser.password)
        }
    }
}
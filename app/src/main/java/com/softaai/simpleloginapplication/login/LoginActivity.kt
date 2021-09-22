package com.softaai.simpleloginapplication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.softaai.simpleloginapplication.MainActivity
import com.softaai.simpleloginapplication.R
import com.softaai.simpleloginapplication.databinding.ActivityLoginBinding
import com.softaai.simpleloginapplication.login.utils.LoginState
import kotlinx.coroutines.flow.collect

class LoginActivity : AppCompatActivity() {

    private lateinit var mViewModel: LoginViewModel

    private lateinit var mViewBinding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        mViewBinding.login.setOnClickListener {
            hideKeyboard(mViewBinding.login)

            mViewModel.doLogin(
                mViewBinding.inputUsername.text.toString(),
                mViewBinding.inputPassword.text.toString()
            )
        }

        observeLoginStates()
    }

    private fun observeLoginStates(){
        lifecycleScope.launchWhenStarted {
            mViewModel.loginStateFlow.collect{
                when(it){
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
                        showMainScreen()
                    }
                }
            }
        }
    }


    private fun setUsernameError() {
        mViewBinding.textInputUsername.error = "Please enter a valid username"
        mViewBinding.textInputUsername.isErrorEnabled = true
    }

    private fun setPasswordError() {
        mViewBinding.textInputPassword.error = "Please enter a valid password"
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
        startActivity(intent)
        finish()
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
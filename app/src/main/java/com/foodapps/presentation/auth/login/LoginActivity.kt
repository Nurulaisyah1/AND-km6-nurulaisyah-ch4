package com.foodapps.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.foodapps.databinding.ActivityLoginBinding
import com.foodapps.presentation.auth.register.RegisterActivity
import com.foodapps.presentation.main.MainActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setClickListeners()
    }

    private fun doLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        viewModel.loginUser(email, password,
            onSuccess = {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                finish()
            },
            onError = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setClickListeners() {
        binding.loginButton.setOnClickListener {
            doLogin()
        }

        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

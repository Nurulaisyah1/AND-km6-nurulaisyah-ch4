package com.foodapps.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.foodapps.databinding.ActivityLoginBinding
import com.foodapps.presentation.register.RegisterActivity
import com.foodapps.utils.GenericViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
        observeResult()
    }

    private fun setupForm() {
        //todo : setup form, hide unnecessary field
    }

    private fun observeResult() {
        //todo : observe login result, show loading state and hide
    }

    private fun navigateToMain() {
        //todo : navigate to main after login
    }

    private fun setClickListeners() {
        // Mendapatkan referensi ke tombol register
        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
        //todo : set click listeners for other buttons
    }

    private fun navigateToRegister() {
        // Intent untuk pindah ke layout pendaftaran (RegisterActivity)
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent) // Memulai activity baru
    }

    private fun doLogin() {
        //todo : do login if form already valid
    }

    private fun isFormValid(): Boolean {
        //todo : check form validation
        return false
    }

    private fun checkEmailValidation(email: String): Boolean {
        //todo : check form validation
        return false
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        //todo : check form validation
        return false
    }
}

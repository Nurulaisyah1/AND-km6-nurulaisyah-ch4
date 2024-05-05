package com.foodapps.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.foodapps.R
import com.foodapps.databinding.ActivityRegisterBinding
import com.foodapps.presentation.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.registrationResult.observe(this) { result ->
            result.onSuccess { user ->
                Toast.makeText(this@RegisterActivity,
                    getString(R.string.register_successfully), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            result.onFailure { exception ->
                Toast.makeText(this@RegisterActivity, "${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                if (password == binding.etConfirmPassword.text.toString()) {
                    viewModel.registerUser(email, password, name)
                } else {
                    Toast.makeText(this@RegisterActivity,
                        getString(R.string.txt_password_failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RegisterActivity,
                    getString(R.string.txt_password_failed2), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

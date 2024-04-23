package com.foodapps.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.foodapps.databinding.ActivityLoginBinding
import com.foodapps.presentation.main.MainActivity
import com.foodapps.presentation.register.RegisterActivity
import com.foodapps.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    private val viewModel: LoginViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        setClickListeners()
        doLogin()
    }


    private fun doLogin(){
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this,MainActivity::class.java).also {
                            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        })

                    } else {
                        // Gagal login
                        // Tangani kesalahan, misalnya, tampilkan pesan kesalahan
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun setClickListeners() {
        // Mendapatkan referensi ke tombol register
        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent) // Memulai activity baru
    }

}

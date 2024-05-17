package com.foodapps.presentation.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodapps.databinding.ActivityRegisterBinding
import com.foodapps.presentation.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener { registerUser() }

        binding.btnNavToLogin.setOnClickListener { navigateToLogin() }
    }

    private fun registerUser() {
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val name = etName.text.toString()
                if (email.isNotBlank() && password.isNotBlank())
                    {
                        if (password == etConfirmPassword.text.toString())
                            {
                                processRegister(email, password, name)
                            } else
                            {
                                Toast.makeText(this@RegisterActivity, "Password tidak sama", Toast.LENGTH_SHORT).show()
                            }
                    } else
                    {
                        Toast.makeText(this@RegisterActivity, "Kolom tidak boleh koson", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun processRegister(
        email: String,
        password: String,
        name: String,
    )  {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val profileUpdates =
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                    val user = auth.currentUser
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { taskUpdate ->
                            if (task.isSuccessful) {
                                startActivity(
                                    Intent(this, LoginActivity::class.java).also {
                                        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
                                    },
                                )
                            } else {
                                Toast.makeText(this, "${taskUpdate.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

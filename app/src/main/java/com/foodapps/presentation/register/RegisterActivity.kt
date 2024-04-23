package com.foodapps.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodapps.databinding.ActivityRegisterBinding
import com.foodapps.presentation.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.btnNavToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun registerUser() {
//        val name = this.binding.et_name.text.toString()
//        val email = this.binding.et_email.text.toString()
//        val password =this.binding.et_password.text.toString()
//        val confirmpassword = this.binding.et_confirm_password.text.toString()

//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Registration success
//                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
//                    // Navigate to the next screen or perform any other actions
//                } else {
//                    // Registration failed
//                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

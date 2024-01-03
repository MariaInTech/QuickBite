package com.example.quickbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbite.com.example.quickbite.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registrationButton: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var forgotPasswordButton: TextView
    private lateinit var fireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        registrationButton = findViewById(R.id.signup)
        emailEditText = findViewById(R.id.emailinput) // Corrected from usernameEditText
        passwordEditText = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordButton = findViewById(R.id.forgotPassword)

        loginButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()

            loginUser(email, password)
        }

        registrationButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainPageActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Login failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

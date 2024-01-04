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

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registrationButton: TextView
    private lateinit var auth: FirebaseAuth
<<<<<<< HEAD
    private lateinit var forgotPasswordButton: TextView
    private lateinit var fireStore: FirebaseFirestore
=======
    private  lateinit var forgotPass: TextView
>>>>>>> 486a26154e910ed75542c1486f7d23fa3e7c7286

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        registrationButton = findViewById(R.id.signup)
        emailEditText = findViewById(R.id.emailinput)
        passwordEditText = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
<<<<<<< HEAD
        forgotPasswordButton = findViewById(R.id.forgotPassword)
=======
        forgotPass = findViewById(R.id.forgotpassword)
>>>>>>> 486a26154e910ed75542c1486f7d23fa3e7c7286

        loginButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()

            loginUser(email, password)
        }

        registrationButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

<<<<<<< HEAD
        forgotPasswordButton.setOnClickListener {
=======

        forgotPass.setOnClickListener {
>>>>>>> 486a26154e910ed75542c1486f7d23fa3e7c7286
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
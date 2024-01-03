package com.example.quickbite.com.example.quickbite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.quickbite.R

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailInputText)
        resetButton = findViewById(R.id.resetPasswordButton)

        resetButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordResetEmail(email)
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity after sending the reset email
                } else {
                    Toast.makeText(this, "Failed to send reset email. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
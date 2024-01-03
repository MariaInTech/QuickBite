package com.example.quickbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbite.LoginActivity
import com.example.quickbite.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUp: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        signUp = findViewById(R.id.signUpButton)
        emailEditText = findViewById(R.id.EmailSignUp)
        passwordEditText = findViewById(R.id.passwordInput)
        usernameEditText = findViewById(R.id.usernameSignUp)
        phoneNumberEditText = findViewById(R.id.PhoneNumberSignUp)

        // Set up signup button click listener
        signUp.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()

            // Call the function to signup the user
            signupUser(email, password, username, phoneNumber)
        }
    }

    private fun signupUser(email: String, password: String, username: String, phoneNumber: String) {
        // Use FirebaseAuth to create a new user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Signup successful
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()

                    val userId = auth.currentUser?.uid

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                    if (userId != null) {
                        val userReference = firestore.collection("/users").document(userId)
                        val userData = hashMapOf(
                            "email" to email,
                            "username" to username,
                            "phoneNumber" to phoneNumber,
                            "geoPoint" to GeoPoint(31.0, 31.0) // Set default latitude and longitude to 31
                        )

                        userReference.set(userData)
                            .addOnSuccessListener {
                                /*val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()*/
                            }
                            .addOnFailureListener { e ->
                                // Handle errors
                                Toast.makeText(this, "Firestore write failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // If signup fails, display a message to the user.
                    Toast.makeText(this, "Signup failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

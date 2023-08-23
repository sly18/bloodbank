package com.example.pulseplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtEmailAddress: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var textRegister: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        edtUsername = findViewById(R.id.edt_username)
        edtEmailAddress = findViewById(R.id.edt_EmailAddress)
        edtPassword = findViewById(R.id.edt_Password)
        btnLogin = findViewById(R.id.but_login)
        textRegister = findViewById(R.id.text_log)

        // Set click listener for login button
        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val email = edtEmailAddress.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            loginUser(username, email, password)
        }

        // Set click listener for register text
        textRegister.setOnClickListener {
            // Navigate to registration page (Page 1)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun loginUser(username: String, email: String, password: String) {
        // Sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Navigate to home page (Page 3)
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this, "Login failed. Please check your credentials.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

package com.example.pulseplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User


class MainActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtCity: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtBloodGroup: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        edtUsername = findViewById(R.id.edt_user)
        edtPassword = findViewById(R.id.edt_pass)
        edtCity = findViewById(R.id.edt_city)
        edtEmail = findViewById(R.id.edt_email)
        edtBloodGroup = findViewById(R.id.edt_blood)
        edtPhoneNumber = findViewById(R.id.edt_phone)
        btnRegister = findViewById(R.id.but_reg)

        // Set click listener for register button
        btnRegister.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val city = edtCity.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val bloodGroup = edtBloodGroup.text.toString().trim()
            val phoneNumber = edtPhoneNumber.text.toString().trim()

            registerUser(username, password, city, email, bloodGroup, phoneNumber)
        }
    }

    private fun registerUser(
        username: String,
        password: String,
        city: String,
        email: String,
        bloodGroup: String,
        phoneNumber: String
    ) {
        // Create a new user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // Save additional user details to Firebase Database
                    saveUserDetails(username, city, bloodGroup, phoneNumber)
                } else {
                    Toast.makeText(
                        this, "Registration failed. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveUserDetails(
        username: String,
        city: String,
        bloodGroup: String,
        phoneNumber: String
    ) {
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        // Save user details to Firebase Database under "users" node using the uid
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid!!)
        val userDetails = User(username, city, bloodGroup, phoneNumber)

        userRef.setValue(userDetails).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                // Navigate to home page (Page 3)
                startActivity(Intent(this, HomePageActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this, "Registration failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

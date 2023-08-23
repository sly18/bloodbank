package com.example.pulseplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pulseplus.User
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class SearchPageActivity : AppCompatActivity() {

    private lateinit var edtCity: EditText
    private lateinit var edtBloodGroup: EditText
    private lateinit var btnFindDonor: Button

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchpage)

        // Initialize views
        edtCity = findViewById(R.id.city)
        edtBloodGroup = findViewById(R.id.bloodgroup)
        btnFindDonor = findViewById(R.id.btnDonor)

        // Initialize Firebase Database reference
        databaseRef = FirebaseDatabase.getInstance().reference

        // Set click listener for find donor button
        btnFindDonor.setOnClickListener {
            val city = edtCity.text.toString().trim()
            val bloodGroup = edtBloodGroup.text.toString().trim()

            searchDonors(city, bloodGroup)
        }
    }

    private fun searchDonors(city: String, bloodGroup: String) {
        // Query Firebase Database for donors matching the given city and blood group
        val query = databaseRef.child("users")
            .orderByChild("cityBloodGroup")
            .equalTo("$city-$bloodGroup")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val donorsList = mutableListOf<User>()

                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    user?.let { donorsList.add(it) }
                }

                // Pass the donorsList to the Display page (Page 5)
                val intent = Intent(this@SearchPageActivity, DisplayPageActivity::class.java)
                intent.putParcelableArrayListExtra("donorsList", ArrayList(donorsList))
                startActivity(intent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Toast.makeText(
                    this@SearchPageActivity, "Error: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

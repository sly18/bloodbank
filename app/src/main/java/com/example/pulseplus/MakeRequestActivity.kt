package com.example.pulseplus

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MakeRequestActivity : AppCompatActivity() {

    private lateinit var edtMessage: EditText
    private lateinit var btnUpload: Button

    private lateinit var storageRef: StorageReference
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makerequest)

        // Initialize views
        edtMessage = findViewById(R.id.edt_message)
        btnUpload = findViewById(R.id.btnUpload)

        // Initialize Firebase Storage and Database references
        storageRef = FirebaseStorage.getInstance().reference
        databaseRef = FirebaseDatabase.getInstance().reference

        // Set click listener for upload button
        btnUpload.setOnClickListener {
            // Open gallery to select an image
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let { uploadImage(it) }
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val imageFileName = "request_${System.currentTimeMillis()}"

        val imageRef = storageRef.child("request_images/$imageFileName")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val imageUrl = task.result.toString()
                val message = edtMessage.text.toString().trim()

                saveRequestToDatabase(imageUrl, message)
            } else {
                Toast.makeText(
                    this, "Image upload failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveRequestToDatabase(imageUrl: String, message: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        val requestRef = databaseRef.child("requests").child(uid!!).push()

        val request = Request(imageUrl, message)
        requestRef.setValue(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Request sent successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(
                    this, "Failed to send request. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
}

package com.example.pulseplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar


class HomePageActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var btnSearch: ImageButton
    private lateinit var btnMakeRequest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Initialize views
        toolbar = findViewById(R.id.tool_bar)
        btnSearch = findViewById(R.id.btnSearch)
        btnMakeRequest = findViewById(R.id.btnUpload)

        // Set toolbar title and options menu
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set click listener for search button
        btnSearch.setOnClickListener {
            // Navigate to search page (Page 4)
            startActivity(Intent(this, SearchPageActivity::class.java))
        }

        // Set click listener for make request button
        btnMakeRequest.setOnClickListener {
            // Navigate to make request page (Page 6)
            startActivity(Intent(this, MakeRequestActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                // Navigate to search page (Page 4)
                startActivity(Intent(this, SearchPageActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

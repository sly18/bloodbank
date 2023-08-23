package com.example.pulseplus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayPageActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaypage)

        // Get the donor list passed from the Search page
        val donorsList = intent.getParcelableArrayListExtra<User>("donorsList") ?: ArrayList()

        // Initialize the ListView and DisplayAdapter
        listView = findViewById(R.id.listView)
        val displayAdapter = DisplayAdapter(this, donorsList)
        listView.adapter = displayAdapter
    }

    private class DisplayAdapter(private val context: Context, private val donorsList: List<User>) : BaseAdapter() {

        override fun getCount(): Int {
            return donorsList.size
        }

        override fun getItem(position: Int): Any {
            return donorsList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity_item_donor, parent, false)

            val donor = donorsList[position]

            val txtUsername = view.findViewById<TextView>(R.id.txtUsername)
            val txtCityBloodGroup = view.findViewById<TextView>(R.id.txtCityBloodGroup)
            val txtPhoneNumber = view.findViewById<TextView>(R.id.txtPhoneNumber)

            txtUsername.text = donor.username
            txtCityBloodGroup.text = "${donor.city}, ${donor.bloodGroup}"
            txtPhoneNumber.text = donor.phoneNumber

            return view
        }
    }
}

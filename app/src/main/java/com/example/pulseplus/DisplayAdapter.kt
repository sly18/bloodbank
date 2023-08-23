package com.example.pulseplus


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DisplayAdapter(private val context: Context, private val donorsList: List<User>) :
    BaseAdapter() {

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
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item_donor, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val user = donorsList[position]

        viewHolder.txtUsername.text = user.username
        viewHolder.txtCityBloodGroup.text = "${user.city}, ${user.bloodGroup}"
        viewHolder.txtPhoneNumber.text = user.phoneNumber

        return view
    }

    private class ViewHolder(view: View) {
        val txtUsername: TextView = view.findViewById(R.id.txtUsername)
        val txtCityBloodGroup: TextView = view.findViewById(R.id.txtCityBloodGroup)
        val txtPhoneNumber: TextView = view.findViewById(R.id.txtPhoneNumber)
    }
}

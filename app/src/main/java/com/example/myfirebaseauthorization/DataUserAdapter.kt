package com.example.myfirebaseauthorization

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.item.view.*

class DataUserAdapter(var list: MutableList<DataUser>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (list != null) return list?.size!!
        else return 0
    }

    override fun onBindViewHolder(vh: ViewHolder, posit: Int) {

        vh?.name?.text = "Имя: " + list?.get(posit).name
        vh?.age?.text = "Возраст: " + list?.get(posit).age
    }

}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name = itemView.name
    var age = itemView.age

}

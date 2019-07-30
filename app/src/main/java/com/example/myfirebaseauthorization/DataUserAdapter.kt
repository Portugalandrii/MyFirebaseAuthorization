package com.example.myfirebaseauthorization

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.user_activity.view.*

class DataUserAdapter(var list: MutableList<DataUser>,var context: Context, var mDatabase: DatabaseReference?): RecyclerView.Adapter<ViewHolder>() {
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
        vh?.item.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            //builder.setTitle("Title")
            builder.setMessage("DELETE or UPDATE")
            builder.setPositiveButton("Delete"){
                    dialog, which ->

                val user =  mDatabase?.child(list?.get(posit)?.key!!)
                user?.removeValue()
            }
            builder.setNeutralButton("Cancel") { dialog, which ->

            }

            builder.setNegativeButton("Update") { dialog, which ->
                val intent = Intent(context,User_activity::class.java)
//                intent.putExtra("name",userList?.get(posit)?.name)

                intent.putExtra("key",list?.get(posit)?.key)
                intent.putExtra("name",list?.get(posit)?.name)
                intent.putExtra("age",list?.get(posit)?.age)
                context.startActivity(intent)

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
    }

}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name = itemView.name
    var age = itemView.age
    var item: LinearLayout = itemView.findViewById(R.id.item)
}

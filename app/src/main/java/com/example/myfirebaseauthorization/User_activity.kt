package com.example.myfirebaseauthorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.user_activity.*

class User_activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    var mAuth = FirebaseDatabase.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    var reference: DatabaseReference? = null
    //1. Объявляем список
    var dataUserList: MutableList<DataUser> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        reference = mAuth!!.getReference("user")

        var recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter:DataUserAdapter = DataUserAdapter(dataUserList)
        recyclerView.adapter = adapter

        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)

        button_save.setOnClickListener {
            val key: String = mDatabase.push().key!!
            var user: DataUser? = DataUser(key, edit_name.text.toString(), edit_age.text.toString())

            //записывыем значение
            mDatabase.child("Info").child(key).setValue(user)
        }

        val userListener = object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

//                var user: DataUser = p0.child("Info").value
                println("??????????????????????????????????${p0.child("Info")}")
//                var index = getItemIndex(user)
//                dataUserList?.set(index, user)
//                adapter.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!${p0.child("Info").value}")
//                var user: DataUser = p0.child("user").child("Info").getValue(DataUser::class.java)!!
//                dataUserList?.add(user)
//                adapter.notifyDataSetChanged()
               // println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!${user.name}")

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                var user: DataUser = p0.getValue(DataUser::class.java)!!
                var index = getItemIndex(user)
                dataUserList?.removeAt(index)
                adapter.notifyDataSetChanged()
            }

        }
        reference!!.addChildEventListener(userListener)
    }
    private fun getItemIndex(user: DataUser): Int {
        var index = -1
        if (this.dataUserList != null) {
            for (i in this!!.dataUserList!!) {
                index++
                if (i.key.equals(user.key))
                    break
            }
        }
        return index
    }

}

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

        var adapter: DataUserAdapter = DataUserAdapter(dataUserList)
        recyclerView.adapter = adapter

        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)

        button_save.setOnClickListener {
            val key: String = mDatabase.push().key!!
            var user: DataUser? = DataUser(edit_name.text.toString(), edit_age.text.toString())

            mDatabase.child(key).child("Info").setValue(user)

        }

        val userListener = object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val children = p0!!.children
                children.forEach {
                    var dataUser: DataUser = it.child("Info").getValue(DataUser::class.java)!!
                    dataUserList?.add(dataUser)

                }
                adapter.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        }
        reference!!.addChildEventListener(userListener)
    }

}

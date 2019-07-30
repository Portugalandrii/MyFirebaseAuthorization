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
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
        var adapter: DataUserAdapter = DataUserAdapter(dataUserList,this, mDatabase)
        recyclerView.adapter = adapter



        button_save.setOnClickListener {
//            val key: String = mDatabase.push().key!!
//            var user: DataUser? = DataUser(key, edit_name.text.toString(), edit_age.text.toString())
//
//            mDatabase.child(key).child("Info").setValue(user)
            if (edit_key.getText().length == 0) {
                if (edit_name.getText().length > 0 && edit_age.getText().length > 0) {


                    var name = edit_name.text.toString()
                    var age = edit_age.text.toString()
                    val key: String = mDatabase.push().key!!
                    var user = key?.let { it1 -> DataUser(it1, name, age) }

                    mDatabase!!.child(key).child("Info").setValue(user)
                    edit_name.setText("")
                    edit_age.setText("")
                    edit_key.setText("")

                }
            } else {
                var key = intent.getStringExtra("key")

                edit_key.setText("")
                var name = edit_name.text.toString()
                var age = edit_age.text.toString()
                mDatabase!!.child(key).child("Info").setValue(DataUser(key, name, age))
                edit_name.setText("")
                edit_age.setText("")
                edit_key.setText("")

            }

        }

        val userListener = object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                dataUserList.clear()

                val children = p0!!.children
                children.forEach {
                    var dataUser: DataUser = it.child("Info").getValue(DataUser::class.java)!!
                    dataUserList?.add(dataUser)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                dataUserList.clear()

                val children = p0!!.children
                children.forEach {
                    var dataUser: DataUser = it.child("Info").getValue(DataUser::class.java)!!
                    dataUserList?.add(dataUser)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        }
        reference!!.addChildEventListener(userListener)
    }
    override fun onResume() {
        super.onResume()

        var key = intent.getStringExtra("key")
        var name = intent.getStringExtra("name")
        var age = intent.getStringExtra("age")

        edit_name.setText(name)
        edit_age.setText(age)
        edit_key.setText(key)

    }
}

package com.example.chatapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var List: ArrayList<Users>
    private lateinit var adapter: Adapter
    private lateinit var auth: FirebaseAuth
    private lateinit var DbRed: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        List = ArrayList()
        adapter = Adapter(this, List)
        auth = FirebaseAuth.getInstance()
        DbRed = FirebaseDatabase.getInstance().getReference()
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        DbRed.child("User").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                for (s in snapshot.children) {
                    val savedUsers = s.getValue(Users::class.java)
                    if (auth.currentUser?.uid!= savedUsers?.uid) {
                        List.add(savedUsers!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}
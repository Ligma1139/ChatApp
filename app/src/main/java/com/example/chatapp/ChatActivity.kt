package com.example.chatapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatBox: EditText
    private lateinit var sendBtn: ImageButton
    private lateinit var text: TextView
    private lateinit var chatList: ArrayList<chat>
    private lateinit var chatAdapter: chatAdapter
    private lateinit var Db: DatabaseReference
    var sender: String? = null
    var receiver: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name = intent.getStringExtra("name")
        val Rid = intent.getStringExtra("uid")
        val Sid = FirebaseAuth.getInstance().currentUser?.uid
        sender = Rid + Sid
        receiver = Sid + Rid

        recyclerView = findViewById(R.id.chatRecyclerView)
        chatBox = findViewById(R.id.chatMessage)
        sendBtn = findViewById(R.id.chatSendMessage)
        chatList = ArrayList()
        text = findViewById(R.id.chatBar)
        chatAdapter = chatAdapter(this, chatList)
        Db = FirebaseDatabase.getInstance().getReference()
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        text.text = name

        Db.child("chats").child(sender!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (s in snapshot.children) {
                        val chat = s.getValue(chat::class.java)
                        chatList.add(chat!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        sendBtn.setOnClickListener {
            val message = chatBox.text.toString()
            val mainMessage = chat(message,Sid)

            Db.child("chats").child(sender!!).child("messages").push()
                .setValue(mainMessage).addOnSuccessListener {
                    Db.child("chats").child(receiver!!).child("messages").push()
                        .setValue(mainMessage)
                }
        }
    }
}




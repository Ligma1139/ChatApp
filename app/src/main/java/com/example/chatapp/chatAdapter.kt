package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class chatAdapter(private val context: Context, private val chatList: ArrayList<chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Receive = 1
    val Sent = 2

    class sentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMessage)
    }
    class receiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.receiveMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive_message,parent,false)
            return receiveViewHolder(view)
        }else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.sent_message,parent,false)
            return sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = chatList[position]
        if(holder.javaClass == sentViewHolder::class.java){
            val viewHolder1 = holder as sentViewHolder
            holder.sentMessage.text = message.message
        }else{
            val viewHolder2 = holder as receiveViewHolder
            holder.receiveMessage.text = message.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = chatList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid == message.senderId){
            return Sent
        }else{
            return Receive
        }
    }

}
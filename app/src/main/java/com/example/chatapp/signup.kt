package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonsign : Button
    private lateinit var nametext : EditText
    private lateinit var emailtext : EditText
    private lateinit var passtext : EditText
    private lateinit var Dbauth : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)
        buttonsign = findViewById(R.id.button_sign_up)
        emailtext = findViewById(R.id.signup_email_input)
        passtext = findViewById(R.id.signup_password_input)
        nametext = findViewById(R.id.signup_username_input)
        auth = FirebaseAuth.getInstance()

        buttonsign.setOnClickListener {
            val name = nametext.text.toString()
            val email = emailtext.text.toString()
            val password = passtext.text.toString()

            firebaseSignup(name, email, password)
        }
    }

    private fun firebaseSignup(name : String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    createUserInDatabase(name,email, auth.currentUser?.uid!!)
                    val Intent = Intent(this,MainActivity::class.java)
                    startActivity(Intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Some error occoured", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun createUserInDatabase(name: String, email: String, uid: String?) {
        Dbauth = FirebaseDatabase.getInstance().getReference()
        Dbauth.child("User").child(uid!!).setValue(Users(name,email, uid))

    }
}
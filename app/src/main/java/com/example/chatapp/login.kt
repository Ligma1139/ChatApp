package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
class login : AppCompatActivity() {
    private lateinit var signupbutton : Button
    private lateinit var loginbutton : Button
    private lateinit var emailText : EditText
    private lateinit var loginpassword : EditText
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        signupbutton = findViewById(R.id.loginsignup)
        loginbutton = findViewById(R.id.button_login)
        emailText = findViewById(R.id.login_username_input)
        loginpassword = findViewById(R.id.login_password_input)
        auth = FirebaseAuth.getInstance()

        signupbutton.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        loginbutton.setOnClickListener {
            val email = emailText.text.toString()
            val password = loginpassword.text.toString()
            firebaseLogin(email,password)
            
        }
    }

    private fun firebaseLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val Intent = Intent(this,MainActivity::class.java)
                    startActivity(Intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Invalid details",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
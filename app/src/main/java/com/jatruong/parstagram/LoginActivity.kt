package com.jatruong.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            login(username, password)
        }

        findViewById<Button>(R.id.signup_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signUp(username, password)
        }
    }

    private fun signUp(username: String, password: String) {
        val user = ParseUser()
        user.username = username
        user.setPassword(password)
        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "${user.username} has sucessfully been created")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error: user creation failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Success: user logged in")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error: login failed", Toast.LENGTH_SHORT).show()
            }
        }))
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish() // prevents back button and closes out loginactivity
    }

    private fun testParseConnection() {
        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{
                Log.d("MainActivity","Object saved.")
            }
        }
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
package com.example.traveldiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.traveldiary.network.LogOut

class LogInActivity : AppCompatActivity(), LogOut {

    private lateinit var editUname: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_activity)

        initRefernces()

        loginBtn.setOnClickListener {
            val username = editUname.text.toString()
            val password = editPassword.text.toString()
            val db = Database(getApplicationContext(), "Healthcare",null,1)

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show()
            } else {
                if(db.login(username,password)==1){
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                    val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.apply()
                    val intent = Intent(this@LogInActivity, HomeActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "Invalid username password ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initRefernces() {
        editUname = findViewById(R.id.editTextUserName)
        editPassword = findViewById(R.id.editTextPassword)
        loginBtn = findViewById(R.id.buttonLogin)
        tv = findViewById(R.id.textViewNewUser)
    }

    override fun backToLogIn() {
        Toast.makeText(this, "Back to Sign in", Toast.LENGTH_LONG).show()
    }
}

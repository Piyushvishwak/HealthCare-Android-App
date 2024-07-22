package com.example.traveldiary

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirm: EditText

    private lateinit var btn: Button
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edUsername = findViewById(R.id.editTextRegUserName)
        edPassword = findViewById(R.id.editTextRegPassword)
        edEmail = findViewById(R.id.editTextRegEmail)
        edConfirm = findViewById(R.id.editTextRegConfirmPassword)
        btn = findViewById(R.id.buttonRegister)
        tv = findViewById(R.id.textViewExistingUser)

        tv.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            val confirmPassword = edConfirm.text.toString()

            val db = Database(getApplicationContext(), "Healthcare",null,1)
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                // Handle registration logic here
                db.register(username,email,password)
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LogInActivity::class.java))
            } else {
                Toast.makeText(this, "Please fill all details correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

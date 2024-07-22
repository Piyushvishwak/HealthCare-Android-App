package com.example.traveldiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userName = findViewById(R.id.userName)
        val username = intent.getStringExtra("username")
        userName.text = "Hello! Mr. $username"

        val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val storedUsername = sharedPreferences.getString("username", "")
        Toast.makeText(applicationContext, "Welcome $storedUsername", Toast.LENGTH_SHORT).show()

        // Setting up card views and their click listeners
        val exit: CardView = findViewById(R.id.cardExit)
        exit.setOnClickListener {
            // Clear SharedPreferences
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Start the LogInActivity
            val intent = Intent(this@HomeActivity, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

        val findDoctor: CardView = findViewById(R.id.cardFindDoctor)
        findDoctor.setOnClickListener {
            startActivity(Intent(this@HomeActivity, FindDoctorActivity::class.java))
        }

        val labTest: CardView = findViewById(R.id.cardLabTest)
        labTest.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LabTestActivity::class.java))
        }
        val healthArticle: CardView = findViewById(R.id.cardHealthDoctor)
        healthArticle.setOnClickListener{
            startActivity(Intent(this@HomeActivity, HealthArticlesActivity::class.java))
        }
        val buyMedicine: CardView = findViewById(R.id.cardBuyMedicine)
        buyMedicine.setOnClickListener{
            startActivity(Intent(this@HomeActivity, BuyMedicines::class.java))
        }
        val orderDetail: CardView = findViewById(R.id.cardOrderDetails)
        orderDetail.setOnClickListener{
            startActivity(Intent(this@HomeActivity, OrderDetails::class.java))
        }
    }
}

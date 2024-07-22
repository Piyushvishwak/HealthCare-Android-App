package com.example.traveldiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckOutDetails : AppCompatActivity() {
    private lateinit var btnBook: Button
    private lateinit var dbHelper: CartDatabaseHelper
    private lateinit var name1: EditText
    private lateinit var address1: EditText
    private lateinit var contNo: EditText
    private lateinit var pinCode: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_details)

        btnBook = findViewById(R.id.btnBookApp)
        dbHelper = CartDatabaseHelper(this)
        name1 = findViewById(R.id.editTextAppFullName)
        address1 = findViewById(R.id.editTextAppAddress)
        contNo = findViewById(R.id.editTextAppContNumber)
        pinCode = findViewById(R.id.editPinCode)
        btnBook.setOnClickListener {
            if(name1.length()==0 || address1.length()==0 || contNo.length()==0 || pinCode.length()==0){
                Toast.makeText(this@CheckOutDetails, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                dbHelper.deleteAllCartItems()
                Toast.makeText(this@CheckOutDetails, "Order Booked Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                }
        }
    }
}

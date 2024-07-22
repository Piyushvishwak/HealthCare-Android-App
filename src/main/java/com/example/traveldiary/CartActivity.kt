package com.example.traveldiary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var checkOutButton: Button
    private lateinit var goBackButton: Button

    private lateinit var dbHelper: CartDatabaseHelper
    private lateinit var cartItems: ArrayList<HashMap<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = CartDatabaseHelper(this)

        listView = findViewById(R.id.listViewCart)
        checkOutButton = findViewById(R.id.checkOut)
        goBackButton = findViewById(R.id.back)

        // Retrieve cart items from the database
        cartItems = dbHelper.getAllCartItems()

        // Define the 'from' and 'to' arrays
        val from = arrayOf("PackageName", "PackageDetails", "PackagePrice")
        val to = intArrayOf(R.id.textViewPackageName, R.id.textViewPackageDetails, R.id.textViewPackagePrice)

        // Create an adapter for the ListView
        val adapter = object : SimpleAdapter(
            this,
            cartItems,
            R.layout.list_item_lab_test,
            from,
            to
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = super.getView(position, convertView, parent)
                val deleteButton: Button = view.findViewById(R.id.buttonAddToCart)
                deleteButton.text = "Delete"
                deleteButton.setOnClickListener {
                    val selectedPackage = cartItems[position]
                    dbHelper.deleteCartItem(selectedPackage["PackageName"] ?: "")
                    cartItems.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(this@CartActivity, "Deleted from Cart: ${selectedPackage["PackageName"]}", Toast.LENGTH_SHORT).show()
                }
                return view
            }
        }

        // Set the adapter to the ListView
        listView.adapter = adapter

        goBackButton.setOnClickListener {
            startActivity(Intent(this@CartActivity, LabTestActivity::class.java))
        }

        checkOutButton.setOnClickListener {
            startActivity(Intent(this, CheckOutDetails::class.java))
        }
    }
}

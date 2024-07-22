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

class LabTestActivity : AppCompatActivity() {

    private val packages = arrayOf(
        arrayOf("Package 1: Full Body Checkup", "Complete Hemogram, HbA1c, Iron Studies, Kidney Function Test, LDH Lactate Dehydrogenase, Serum, Lipid Profile, Liver Function Test", "999"),
        arrayOf("Package 2: Blood Glucose Fasting", "Blood Glucose Fasting", "299"),
        arrayOf("Package 3: COVID-19 Antibody IgG", "COVID-19 Antibody IgG", "899"),
        arrayOf("Package 4: Thyroid Check", "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)", "499"),
        arrayOf("Package 5: Immunity Check", "Complete Hemogram, CRP (C Reactive Protein) Quantitative, Serum, Iron Studies, Kidney Function Test, Vitamin D Total-25 Hydroxy, Liver Function Test, Lipid Profile", "699")
    )

    private lateinit var listView: ListView
    private lateinit var btnGoToCart: Button
    private lateinit var btnBack: Button

    private lateinit var dbHelper: CartDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test)

        dbHelper = CartDatabaseHelper(this)

        listView = findViewById(R.id.listViewLT)
        btnGoToCart = findViewById(R.id.buttonLTGoToCart)
        btnBack = findViewById(R.id.buttonLTBack)

        // Create a list of HashMaps for each package
        val dataList = mutableListOf<HashMap<String, String>>()
        for (i in packages.indices) {
            val item = HashMap<String, String>()
            item["PackageName"] = packages[i][0]
            item["PackageDetails"] = packages[i][1]
            item["PackagePrice"] = "₹${packages[i][2]}"
            dataList.add(item)
        }

        val from = arrayOf("PackageName", "PackageDetails", "PackagePrice")
        val to = intArrayOf(R.id.textViewPackageName, R.id.textViewPackageDetails, R.id.textViewPackagePrice)

        // Create an adapter for the ListView
        val adapter = object : SimpleAdapter(
            this,
            dataList,
            R.layout.list_item_lab_test,
            from,
            to
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = super.getView(position, convertView, parent)
                val addToCartButton: Button = view.findViewById(R.id.buttonAddToCart)
                addToCartButton.setOnClickListener {
                    val selectedPackage = dataList[position]
                    dbHelper.addCartItem(selectedPackage)
                    Toast.makeText(this@LabTestActivity, "Added to Cart: ${selectedPackage["PackageName"]}", Toast.LENGTH_SHORT).show()
                }
                return view
            }
        }

        // Set the adapter to the ListView
        listView.adapter = adapter

        btnBack.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, HomeActivity::class.java))
        }

        btnGoToCart.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, CartActivity::class.java))
        }
    }
}

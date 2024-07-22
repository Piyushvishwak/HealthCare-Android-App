package com.example.traveldiary

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveldiary.model.UserResponse
import com.example.traveldiary.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DoctorDetailsActivity : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var tv: TextView
    private lateinit var searchEditText: EditText
    private var names: List<String> = emptyList()
    private lateinit var adapter: DoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        btn = findViewById(R.id.buttonDDBack)
        tv = findViewById(R.id.textViewDDTitle)
        searchEditText = findViewById(R.id.textDDSearch)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewDD)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

        adapter = DoctorAdapter(emptyList()) { selectedName ->
            val bookIntent = Intent(this@DoctorDetailsActivity, BookAppointment::class.java)
            bookIntent.putExtra("doctorName", selectedName)
            bookIntent.putExtra("title", tv.text.toString())
            startActivity(bookIntent)
        }
        recyclerView.adapter = adapter

        val intent = intent
        val title = intent.getStringExtra("title") ?: "Default Title"
        tv.text = title

        btn.setOnClickListener {
            startActivity(Intent(this@DoctorDetailsActivity, FindDoctorActivity::class.java))
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Make the network call
        apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.results ?: emptyList()
                    names = users.map { "${it.name.title} ${it.name.first} ${it.name.last}" }

                    // Update adapter with new data
                    adapter = DoctorAdapter(names) { selectedName ->
                        val bookIntent = Intent(this@DoctorDetailsActivity, BookAppointment::class.java)
                        bookIntent.putExtra("doctorName", selectedName)
                        bookIntent.putExtra("title", title)
                        startActivity(bookIntent)
                    }
                    recyclerView.adapter = adapter

                } else {
                    Toast.makeText(this@DoctorDetailsActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@DoctorDetailsActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filter(text: String) {
        val filteredNames = if (text.isEmpty()) {
            names
        } else {
            val query = text.lowercase()
            names.filter { it.lowercase().contains(query) }
        }

        // Update adapter with filtered data
        adapter = DoctorAdapter(filteredNames) { selectedName ->
            val bookIntent = Intent(this@DoctorDetailsActivity, BookAppointment::class.java)
            bookIntent.putExtra("doctorName", selectedName)
            bookIntent.putExtra("title", tv.text.toString())
            startActivity(bookIntent)
        }
        findViewById<RecyclerView>(R.id.recyclerViewDD).adapter = adapter
    }
}

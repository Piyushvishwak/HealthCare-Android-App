package com.example.traveldiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class HealthArticlesActivity : AppCompatActivity() {

    private val healthDetails = arrayOf(
        arrayOf("Walking Daily ", "", "", "Click More Details"),
        arrayOf("Home care of COVID-19 ", "", "", "Click More Details"),
        arrayOf("Stop Smoking ", "", "", "Click More Details"),
        arrayOf("Menstrual Cramps ", "", "", "Click More Details"),
        arrayOf("Healthy Gut ", "", "", "Click More Details")
    )

    private val images = intArrayOf(
        R.drawable.health1,
        R.drawable.health2,
        R.drawable.health3,
        R.drawable.health4,
        R.drawable.health5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_articles)

        val listView: ListView = findViewById(R.id.listView)
        val btnBack: Button = findViewById(R.id.buttonBack)

        btnBack.setOnClickListener {
            startActivity(Intent(this@HealthArticlesActivity, HomeActivity::class.java))
        }

        val list = ArrayList<HashMap<String, String>>()

        for (details in healthDetails) {
            val item = HashMap<String, String>()
            item["title"] = details[0]
            item["more_details"] = details[3]
            list.add(item)
        }

        val sa = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("title", "more_details"),
            intArrayOf(R.id.title, R.id.more_details)
        )

        listView.adapter = sa

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@HealthArticlesActivity, HealthArticleDetailsActivity::class.java)
            intent.putExtra("title", healthDetails[position][0])
            intent.putExtra("image", images[position])
            startActivity(intent)
        }
    }
}

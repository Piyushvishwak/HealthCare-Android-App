package com.example.traveldiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HealthArticleDetailsActivity : AppCompatActivity() {
    private lateinit var healthArticleDetailTitle: EditText
    private lateinit var healthArticle: ImageView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_article_details)

        btn = findViewById(R.id.buttonBack)
        healthArticleDetailTitle = findViewById(R.id.healthArticleDetailTitle)
        healthArticle = findViewById(R.id.healthArticle)

        val intent = intent
        val title = intent.getStringExtra("title")
        val imageResId = intent.getIntExtra("image", 0) // Use default value of 0 if not found

        healthArticleDetailTitle.setText(title)
        if (imageResId != 0) {
            healthArticle.setImageResource(imageResId)
        }

        btn.setOnClickListener {
            startActivity(Intent(this@HealthArticleDetailsActivity, HealthArticlesActivity::class.java))
        }
    }
}

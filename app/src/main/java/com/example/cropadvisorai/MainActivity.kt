package com.example.cropadvisorai

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var soilType: EditText
    private lateinit var nitrogen: EditText
    private lateinit var ph: EditText
    private lateinit var temp: EditText
    private lateinit var rainfall: EditText
    private lateinit var humidity: EditText
    private lateinit var btnRecommend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soilType = findViewById(R.id.soilType)
        nitrogen = findViewById(R.id.nitrogen)
        ph = findViewById(R.id.ph)
        temp = findViewById(R.id.temp)
        rainfall = findViewById(R.id.rainfall)
        humidity = findViewById(R.id.humidity)
        btnRecommend = findViewById(R.id.btnRecommend)

        btnRecommend.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("soil", soilType.text.toString())
            intent.putExtra("nitrogen", nitrogen.text.toString())
            intent.putExtra("ph", ph.text.toString())
            intent.putExtra("temp", temp.text.toString())
            intent.putExtra("rainfall", rainfall.text.toString())
            intent.putExtra("humidity", humidity.text.toString())
            startActivity(intent)
        }
    }
}

package com.example.cropadvisorai

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val txtSummary: TextView = findViewById(R.id.txtSummary)

        val soil = intent.getStringExtra("soil")
        val nitrogen = intent.getStringExtra("nitrogen")
        val ph = intent.getStringExtra("ph")
        val temp = intent.getStringExtra("temp")
        val rainfall = intent.getStringExtra("rainfall")
        val humidity = intent.getStringExtra("humidity")

        txtSummary.text = """
            🌾 Soil Type: $soil
            💧 Nitrogen: $nitrogen%
            ⚖️ pH: $ph
            🌤 Temperature: $temp°C
            🌦 Rainfall: $rainfall mm
            💨 Humidity: $humidity%
        """.trimIndent()
    }
}

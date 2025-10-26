package com.example.cropadvisorai

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ResultActivity : AppCompatActivity() {

    private lateinit var txtSummary: TextView
    private val apiKey = "" // Your API key will be managed by the Canvas environment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        txtSummary = findViewById(R.id.txtSummary)
        val inputSummary = intent.getStringExtra("inputSummary") ?: "No input data provided."

        // Display the collected input summary
        txtSummary.text = "Analyzing conditions...\n\n$inputSummary"

        // Trigger the AI recommendation
        if (inputSummary.isNotBlank()) {
            getAiRecommendation(inputSummary)
        }
    }

    private fun getAiRecommendation(inputSummary: String) {
        // Define the instruction and the specific task for the LLM
        val systemInstruction = "Act as a leading, experienced agricultural scientist and provide precise crop advice. You MUST recommend the SINGLE BEST CROP and provide a brief, professional justification for the recommendation based ONLY on the provided soil and climate data."
        val userPrompt = "Based on the following conditions, recommend the best crop and justify your choice:\n\n$inputSummary"

        // This is a complex prompt, wrap it for structured output
        val jsonSchema = """
            {
                "type": "OBJECT",
                "properties": {
                    "recommendedCrop": { "type": "STRING", "description": "The single best crop name." },
                    "justification": { "type": "STRING", "description": "A brief, professional justification explaining why this crop is best for the given conditions (soil, N, pH, temp, rainfall, humidity)." },
                    "alertLevel": { "type": "STRING", "enum": ["Low Risk", "Medium Risk", "High Risk"], "description": "Overall risk assessment for planting this crop under these conditions." }
                }
            }
        """.trimIndent()

        // Prepare the API payload
        val payload = JSONObject().apply {
            put("contents", JSONObject().apply {
                put("parts", JSONObject().apply {
                    put("text", userPrompt)
                })
            })
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONObject().apply {
                    put("text", systemInstruction)
                })
            })
            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
                put("responseSchema", JSONObject(jsonSchema))
            })
        }.toString()

        // Use Coroutines (Unit II Syllabus) for API call
        lifecycleScope.launch {
            try {
                val responseJson = withContext(Dispatchers.IO) {
                    performApiCall(payload)
                }
                processAiResponse(responseJson)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    txtSummary.text = "❌ AI Analysis Failed: ${e.message}. Ensure network connection is active."
                    Toast.makeText(this@ResultActivity, "API Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun performApiCall(payload: String): JSONObject {
        // This function handles the network call with exponential backoff (simplified here)
        val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-preview-09-2025:generateContent?key=$apiKey"
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        connection.outputStream.use { os ->
            val input = payload.toByteArray(Charsets.UTF_8)
            os.write(input, 0, input.size)
        }

        return try {
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }.let { JSONObject(it) }
            } else {
                val error = connection.errorStream.bufferedReader().use { it.readText() }
                throw Exception("API returned error code ${connection.responseCode}: $error")
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun processAiResponse(response: JSONObject) {
        try {
            val candidate = response.getJSONArray("candidates").getJSONObject(0)
            val content = candidate.getJSONObject("content")
            val parts = content.getJSONArray("parts")
            val jsonText = parts.getJSONObject(0).getString("text")

            val jsonObject = JSONObject(jsonText)
            val crop = jsonObject.getString("recommendedCrop")
            val justification = jsonObject.getString("justification")
            val alert = jsonObject.getString("alertLevel")

            // Update UI on the main thread
            runOnUiThread {
                txtSummary.text = """
                    ✅ AI Recommendation Complete!

                    Best Crop: $crop 🌾
                    Risk Level: $alert

                    Justification:
                    $justification
                """.trimIndent()

                Toast.makeText(this, "Recommended Crop: $crop", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            runOnUiThread {
                txtSummary.text = "Error processing AI response: ${e.message}"
            }
        }
    }
}

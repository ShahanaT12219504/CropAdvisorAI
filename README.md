# 🌱 CropAdvisor AI

CropAdvisor AI is an Android-based intelligent agriculture assistant that helps users make informed farming decisions using AI-powered crop recommendations, an agricultural chat assistant, and task management tools.  
The application is built with Kotlin and integrates Google Gemini API for AI responses along with Firebase Authentication for user management.

---

## 🎯 Objective

The goal of CropAdvisor AI is to assist farmers and agriculture enthusiasts by:
- Recommending the most suitable crop based on soil and climate conditions
- Providing AI-based agricultural guidance through chat
- Helping users manage farming-related reminders and checklists
- Delivering a clean, mobile-friendly decision support system

---

## 🧠 Features

### 🔐 User Authentication
- Firebase Login & Signup
- Email and password validation
- Persistent user sessions

### 🌾 AI Crop Recommendation
Users provide:
- Soil Type
- Nitrogen Percentage
- Soil pH
- Temperature
- Rainfall
- Humidity

AI Output:
- Best suitable crop
- Risk/alert level
- Scientific justification

### 🤖 AI Agri Chat
- Chat-style interface
- Ask questions about crops, pests, diseases, and climate
- Responses generated using Google Gemini API

### ✅ Agricultural Checklist
- Add, remove, and manage farming tasks
- Useful for reminders like soil testing, rainfall checks, and crop preparation

### 👤 Profile Section
- Displays logged-in user details
- Secure logout functionality

---

## 🛠️ Tech Stack

| Layer | Technology |
|-----|-----------|
| Language | Kotlin |
| IDE | Android Studio |
| UI | XML |
| Authentication | Firebase Auth |
| AI | Google Gemini API |
| Networking | Retrofit |
| Architecture | Fragment-based with Bottom Navigation |
| Build System | Gradle (Kotlin DSL) |

---

## 🧩 App Architecture

- SplashActivity  
- LoginActivity / SignupActivity  
- MainActivity (Bottom Navigation Host)  
- Fragments:
  - Home (Crop input & recommendation)
  - AI Chat
  - Checklist
  - Profile  
- ResultActivity (AI crop recommendation output)

Architecture separates UI, API handling, and authentication logic for better maintainability.

---

## 🔌 AI & API Integration

- Gemini API used for:
  - Crop recommendation analysis
  - Agricultural AI chat
- Retrofit handles network communication
- Structured prompt and response handling using data models

---

## ▶️ How to Run

1. Clone the repository

git clone <repository-url>

2. Open the project in Android Studio

3. Add required configuration files:

    - google-services.json for Firebase Authentication

    - Google Gemini API key (via local.properties or BuildConfig)

4. Sync the project with Gradle

5. Run the application on an Android Emulator or Physical Device

---

## 🔑 API Key Setup (Required)

You must provide your own Gemini API key.

Recommended:
- Use `local.properties` or `BuildConfig`

Temporary (testing only):
```kotlin
private val apiKey = "YOUR_GEMINI_API_KEY"

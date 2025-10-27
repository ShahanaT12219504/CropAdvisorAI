package com.example.cropadvisorai

data class GeminiRequest(val contents: List<ApiContent>)
data class Content(val parts: List<ApiPart>)
data class Part(val text: String)

data class GeminiResponse(val candidates: List<Candidate>)
data class Candidate(val content: ApiContent)

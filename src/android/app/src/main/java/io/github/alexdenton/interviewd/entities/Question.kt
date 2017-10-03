package io.github.alexdenton.interviewd.entities

data class Question(
        val id: Int,
        val name: String,
        val description: String,
        val timeEstimate: Int
)
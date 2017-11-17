package io.github.alexdenton.interviewd.entities

data class Question(
        val id: Long,
        val name: String,
        val description: String,
        val timeEstimate: Int
)
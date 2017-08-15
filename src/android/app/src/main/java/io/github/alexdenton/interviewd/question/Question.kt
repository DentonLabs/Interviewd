package io.github.alexdenton.interviewd.question

data class Question(
        val id: Int,
        val name: String,
        val description: String,
        val timeEstimate: Int
)
package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/11/17.
 */
data class QuestionDto(
        val id: Int,
        val name: String,
        val description: String
) {
    fun toQuestion(): Question = Question(id, name, description, 10) //TODO: Replace this once the api supports time estimates
}
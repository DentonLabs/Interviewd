package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.entities.Question

/**
 * Created by ryan on 8/11/17.
 */
data class QuestionDto(
        val id: Int,
        val name: String,
        val description: String,
        val estimate: Int
) {
    fun toQuestion(): Question = Question(id, name, description, estimate) //TODO: Replace this once the api supports time estimates
}
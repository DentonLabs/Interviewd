package io.github.alexdenton.interviewd.interview

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 7/24/17.
 */
data class Interview(
        val name: String,
        val questions: List<Question>
)
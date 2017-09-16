package io.github.alexdenton.interviewd.interview

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/10/17.
 */

data class Template(
        val name: String,
        val questions: List<Question>
){
    override fun toString(): String = name
}
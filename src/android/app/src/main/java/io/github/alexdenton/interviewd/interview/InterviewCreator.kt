package io.github.alexdenton.interviewd.interview

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 7/24/17.
 */
class InterviewCreator(val name: String) {
    val internalList = mutableListOf<Question>()

    fun  addQuestion(question: Question) = internalList.add(question)
    fun  create(): Interview = Interview(name, internalList.toList())
}
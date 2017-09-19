package io.github.alexdenton.interviewd.bus.events

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 9/18/17.
 */
data class SendPickedQuestionsEvent(val pickedQuestions: List<Question>)
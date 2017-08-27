package io.github.alexdenton.interviewd.bus.events

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/26/17.
 */
data class SendQuestionListEvent(val list: List<Question>)
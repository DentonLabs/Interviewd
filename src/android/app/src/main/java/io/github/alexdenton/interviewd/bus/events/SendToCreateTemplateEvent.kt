package io.github.alexdenton.interviewd.bus.events

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/25/17.
 */
data class SendToCreateTemplateEvent(val list: List<Question>)
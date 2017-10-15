package io.github.alexdenton.interviewd.template.create.events

import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.bus.events.Event
import io.github.rfonzi.rxaware.bus.events.EventID

/**
 * Created by ryan on 10/14/17.
 */
data class ToQuestionBankEvent(val checkedQuestions: List<Question>) : Event {
    override val id: EventID
        get() = EventID.valueOf(checkedQuestions.toString())
}
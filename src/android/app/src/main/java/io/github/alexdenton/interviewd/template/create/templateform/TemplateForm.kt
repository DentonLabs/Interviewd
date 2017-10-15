package io.github.alexdenton.interviewd.template.create.templateform

import io.github.alexdenton.interviewd.entities.Question

/**
 * Created by ryan on 10/14/17.
 */
interface TemplateForm {
    fun useCheckedQuestions(checkedQuestions: List<Question>)
    fun getCheckedQuestions(): List<Question>
}
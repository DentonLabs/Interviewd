package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.interview.Template

/**
 * Created by ryan on 8/14/17.
 */
data class TemplateDto (val id: Int, val name: String, val questions: List<QuestionDto>){
    fun toTemplate() = Template(name, questions.map { it.toQuestion() })
}
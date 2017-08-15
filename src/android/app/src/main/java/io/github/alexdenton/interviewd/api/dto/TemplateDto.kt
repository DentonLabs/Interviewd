package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.interview.Template

/**
 * Created by ryan on 8/14/17.
 */
data class TemplateDto (val name: String, val questions: List<QuestionDto>, val id: Int = 0){
    fun toTemplate() = Template(name, questions.map { it.toQuestion() })
}
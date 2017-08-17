package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

/**
 * Created by ryan on 8/14/17.
 */
class TemplateRetrofitRepository : TemplateRepository {

    val client: InterviewdApiService = RetrofitFactory().create(RetrofitFactory.Mode.Local)

    override fun getAllTemplates(): Single<List<Template>>
            = client.getTemplates()
            .map { it.map { it.toTemplate() } }

    override fun createTemplate(template: Template): Single<Template>
        = client.createTemplate(template.toDto())
            .map { it.toTemplate() }

    fun List<Question>.toIdList() = map { QuestionDto(it.id, it.name, it.description) }
    fun Template.toDto() = TemplateDto(name, questions.toIdList())
}
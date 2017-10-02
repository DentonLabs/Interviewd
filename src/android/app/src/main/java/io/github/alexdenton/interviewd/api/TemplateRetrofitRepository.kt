package io.github.alexdenton.interviewd.api

import android.content.Context
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

/**
 * Created by ryan on 8/14/17.
 */
class TemplateRetrofitRepository(context: Context) : TemplateRepository {

    val client: InterviewdApiService = RetrofitFactory(context).create(RetrofitFactory.Mode.Demo)

    override fun getTemplate(id: Int): Single<Template>
            = client.getTemplate(id)
            .map { it.toTemplate() }

    override fun getAllTemplates(): Single<List<Template>>
            = client.getTemplates()
            .map { it.map { it.toTemplate() } }

    override fun createTemplate(template: Template): Single<Template>
            = client.createTemplate(template.toDto())
            .map { it.toTemplate() }

    override fun updateTemplate(template: Template): Single<Template>
            = client.patchTemplate(template.id, template.toDto())
            .map { it.toTemplate() }

    fun List<Question>.toIdList() = map { QuestionDto(it.id, it.name, it.description, it.timeEstimate) }
    fun Template.toDto() = TemplateDto(name, questions.toIdList(), id)
}
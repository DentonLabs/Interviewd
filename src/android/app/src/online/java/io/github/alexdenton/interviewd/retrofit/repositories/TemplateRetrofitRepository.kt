package io.github.alexdenton.interviewd.retrofit.repositories

import android.content.Context
import io.github.alexdenton.interviewd.api.InterviewdApi
import io.github.alexdenton.interviewd.retrofit.RetrofitFactory
import io.github.alexdenton.interviewd.retrofit.dto.TemplateDto
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.retrofit.InterviewdApiRetrofit
import io.reactivex.Single

/**
 * Created by ryan on 8/14/17.
 */
class TemplateRetrofitRepository(context: Context) : TemplateRepository {

    val client: InterviewdApiRetrofit = RetrofitFactory(context).create(RetrofitFactory.Mode.Local)

    override fun getTemplate(id: Int): Single<Template>
            = client.getTemplate(id)
            .map { it.toTemplateBlocking() }

    override fun getAllTemplates(): Single<List<Template>>
            = client.getTemplates()
            .map { it.map { it.toTemplateBlocking() } }

    override fun createTemplate(template: Template): Single<Template>
            = client.createTemplate(template.toDto())
            .map { it.toTemplateBlocking() }

    override fun updateTemplate(template: Template): Single<Template>
            = client.patchTemplate(template.id, template.toDto())
            .map { it.toTemplateBlocking() }

    override fun deleteTemplate(id: Int): Single<Template>
            = client.deleteTemplate(id)
            .map { it.toTemplateBlocking() }

    fun List<Question>.toIdList() = map { it.id }
    fun Template.toDto() = TemplateDto(name, questions.toIdList().map { it.toInt() }, id)
    fun TemplateDto.toTemplateBlocking() = Template(name, questionIds.map { client.getQuestion(it.toLong()).blockingGet().toQuestion() }, id)
}
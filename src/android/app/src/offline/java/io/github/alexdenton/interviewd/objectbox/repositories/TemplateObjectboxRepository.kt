package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class TemplateObjectboxRepository(val client: InterviewdObjectboxApi) : TemplateRepository {
    override fun getTemplate(id: Long): Single<Template> {
        return client.getTemplate(id).map { it.toTemplate() }
    }

    override fun getAllTemplates(): Single<List<Template>> {
        return client.getTemplates().map { it.map { it.toTemplate() } }
    }

    override fun createTemplate(template: Template): Single<Template> {
        return client.createTemplate(template.toEntity()).map { it.toTemplate() }
    }

    override fun updateTemplate(template: Template): Single<Template> {
        return client.getTemplate(template.id)
                .flatMap { client.patchTemplate(it.id, it.updateFrom(template)) }
                .map { it.toTemplate() }
    }

    override fun deleteTemplate(id: Long): Single<Template> {
        return client.deleteTemplate(id).map { it.toTemplate() }
    }

}
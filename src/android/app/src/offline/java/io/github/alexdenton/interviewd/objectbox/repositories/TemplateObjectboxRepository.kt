package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class TemplateObjectboxRepository(val client: InterviewdObjectboxApi) : TemplateRepository {
    override fun getTemplate(id: Int): Single<Template> {
        return Single.never()
    }

    override fun getAllTemplates(): Single<List<Template>> {
        return Single.never()
    }

    override fun createTemplate(template: Template): Single<Template> {
        return Single.never()
    }

    override fun updateTemplate(template: Template): Single<Template> {
        return Single.never()
    }

    override fun deleteTemplate(id: Int): Single<Template> {
        return Single.never()
    }
}
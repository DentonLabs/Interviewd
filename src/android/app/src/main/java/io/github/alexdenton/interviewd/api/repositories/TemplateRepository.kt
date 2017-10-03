package io.github.alexdenton.interviewd.api.repositories

import io.github.alexdenton.interviewd.entities.Template
import io.reactivex.Single

/**
 * Created by ryan on 8/14/17.
 */
interface TemplateRepository {
    fun getTemplate(id: Int): Single<Template>
    fun getAllTemplates(): Single<List<Template>>
    fun createTemplate(template: Template): Single<Template>
    fun updateTemplate(template: Template): Single<Template>
}
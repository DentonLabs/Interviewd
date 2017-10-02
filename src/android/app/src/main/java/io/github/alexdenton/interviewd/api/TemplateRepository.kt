package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.interview.Template
import io.reactivex.Single

/**
 * Created by ryan on 8/14/17.
 */
interface TemplateRepository {
    fun getTemplate(id: Int): Single<Template>
    fun getAllTemplates(): Single<List<Template>>
    fun createTemplate(template: Template): Single<Template>
}
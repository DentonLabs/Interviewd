package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.objectbox.dto.CandidateEntity
import io.github.alexdenton.interviewd.objectbox.dto.QuestionEntity
import io.github.alexdenton.interviewd.objectbox.dto.TemplateEntity

/**
 * Created by ryan on 11/15/17.
 */

fun Question.toEntity() = QuestionEntity(id, name, description, timeEstimate)

fun TemplateEntity.toTemplate(): Template {
    val questionList: List<Question> = questions.map { it.toQuestion() }
    return Template(name, questionList, id)
}

fun TemplateEntity.updateFrom(template: Template): TemplateEntity {
    this.name = template.name
    this.questions.apply {
        clear()
        addAll(template.questions.map { it.toEntity() })
        // TODO define some kind of comparator so entities aren't automatically sorted by ObjectBox id
    }
    return this
}

fun Template.toEntity(): TemplateEntity {
    val entity = TemplateEntity(this.id, this.name)

    entity.questions.addAll(this.questions.map { it.toEntity() })

    return entity
}

fun Candidate.toEntity() = CandidateEntity(id, firstName, lastName)
package io.github.alexdenton.interviewd.objectbox.dto

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * Created by ryan on 11/14/17.
 */

@Entity
data class TemplateEntity(
        @Id var id: Long = 0,
        var name: String = ""
){
    lateinit var questions: ToMany<QuestionEntity>
}
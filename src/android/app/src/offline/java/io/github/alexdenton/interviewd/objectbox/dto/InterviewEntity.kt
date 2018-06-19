package io.github.alexdenton.interviewd.objectbox.dto

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

/**
 * Created by ryan on 11/14/17.
 */

@Entity
data class InterviewEntity(
        @Id var id: Long = 0,
        var name: String = ""
){
    lateinit var candidate: ToOne<CandidateEntity>
    lateinit var questions: ToMany<QuestionEntity>
}
package io.github.alexdenton.interviewd.objectbox.dto

import io.github.alexdenton.interviewd.entities.Question
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by ryan on 11/14/17.
 */

@Entity
data class QuestionEntity(
        @Id var id: Long = 0,
        val name: String,
        val description: String,
        val estimate: Int
){
    fun toQuestion() = Question(id, name, description, estimate)
}
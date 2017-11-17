package io.github.alexdenton.interviewd.api.repositories

import io.github.alexdenton.interviewd.entities.Question
import io.reactivex.Single

/**
 * Created by ryan on 8/11/17.
 */
interface QuestionRepository {
    fun getQuestion(id: Long): Single<Question>
    fun getAllQuestions(): Single<List<Question>>
    fun createQuestion(question: Question): Single<Question>
    fun updateQuestion(question: Question): Single<Question>
    fun deleteQuestion(id: Long): Single<Question>
}
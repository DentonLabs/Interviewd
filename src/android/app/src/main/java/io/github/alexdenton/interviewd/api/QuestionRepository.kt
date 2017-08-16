package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

/**
 * Created by ryan on 8/11/17.
 */
interface QuestionRepository {
    fun getQuestion(id: Int): Single<Question>
    fun getAllQuestions(): Single<List<Question>>
    fun createQuestion(question: Question): Single<Question>
}
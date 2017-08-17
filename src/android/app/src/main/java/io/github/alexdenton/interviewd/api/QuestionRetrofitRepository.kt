package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

/**
 * Created by ryan on 8/11/17.
 */


class QuestionRetrofitRepository : QuestionRepository {

    val client: InterviewdApiService = RetrofitFactory().create(RetrofitFactory.Mode.Local)

    override fun getQuestion(id: Int): Single<Question>
            = client.getQuestion(id)
            .map { it.toQuestion() }

    override fun getAllQuestions(): Single<List<Question>>
            = client.getQuestions()
            .map { it.map { it.toQuestion() } }

    override fun createQuestion(question: Question): Single<Question>
            = client.createQuestion(question.toDto())
            .map { it.toQuestion() }

    fun Question.toDto() = QuestionDto(0, name, description)
}
package io.github.alexdenton.interviewd.retrofit.repositories

import android.content.Context
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.retrofit.InterviewdApiRetrofit
import io.github.alexdenton.interviewd.retrofit.RetrofitFactory
import io.github.alexdenton.interviewd.retrofit.dto.QuestionDto
import io.reactivex.Single

/**
 * Created by ryan on 8/11/17.
 */


class QuestionRetrofitRepository(val context: Context) : QuestionRepository {

    val client: InterviewdApiRetrofit = RetrofitFactory(context).create(RetrofitFactory.Mode.Local)

    override fun getQuestion(id: Long): Single<Question>
            = client.getQuestion(id)
            .map { it.toQuestion() }

    override fun getAllQuestions(): Single<List<Question>>
            = client.getQuestions()
            .map { it.map { it.toQuestion() } }

    override fun createQuestion(question: Question): Single<Question>
            = client.createQuestion(question.toDto())
            .map { it.toQuestion() }

    override fun updateQuestion(question: Question): Single<Question>
            = client.patchQuestion(question.id, question.toDto())
            .map { it.toQuestion() }

    override fun deleteQuestion(id: Long): Single<Question>
            = client.deleteQuestion(id)
            .map { it.toQuestion() }

    fun Question.toDto() = QuestionDto(id, name, description, timeEstimate)
}
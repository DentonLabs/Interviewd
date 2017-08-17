package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ryan on 8/11/17.
 */


class QuestionRetrofitRepository : QuestionRepository {
    val local: String = "http://192.168.86.26:9005"

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(local)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val client: InterviewdApiService = retrofit.create(InterviewdApiService::class.java)

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
package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ryan on 8/11/17.
 */


class QuestionRepositoryImpl : QuestionRepository {
    val local: String = "http://10.0.2.2:9005"

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(local)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val client: InterviewdApiService = retrofit.create(InterviewdApiService::class.java)

    override fun getQuestion(id: Int): Question {
        TODO()
    }

    override fun getAllQuestions(): Single<List<Question>> {

        return client.getQuestions()
                .map { it.map { it.toQuestion() } }

    }


}
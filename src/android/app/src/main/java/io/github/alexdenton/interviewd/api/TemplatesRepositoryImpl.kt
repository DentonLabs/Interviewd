package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.interview.Template
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ryan on 8/14/17.
 */
class TemplatesRepositoryImpl : TemplatesRepository {
    val local: String = "http://10.0.2.2:9005"

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(local)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val client: InterviewdApiService = retrofit.create(InterviewdApiService::class.java)

    override fun getAllTemplates(): Single<List<Template>>
            = client.getTemplates()
            .map { it.map { it.toTemplate() } }
}
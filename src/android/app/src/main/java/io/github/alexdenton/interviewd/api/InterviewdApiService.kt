package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.CandidateDto
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by ryan on 8/11/17.
 */
interface InterviewdApiService {

    @GET("/questions")
    fun getQuestions(): Single<List<QuestionDto>>

    @GET("/questions/{id}")
    fun getQuestion(@Path("id") id: Int): Single<QuestionDto>

    @POST("/questions")
    fun createQuestion(@Body question: QuestionDto): Single<QuestionDto>

    @GET("/templates")
    fun getTemplates(): Single<List<TemplateDto>>

    @POST("/templates")
    fun createTemplate(@Body template: TemplateDto): Single<TemplateDto>

    @GET("/candidates")
    fun getCandidates(): Single<List<CandidateDto>>

    @GET("/candidates/{id}")
    fun getCandidate(@Path("id") id: Int): Single<CandidateDto>

    @POST("/candidates")
    fun createCandidate(@Body candidate: CandidateDto): Single<CandidateDto>
}
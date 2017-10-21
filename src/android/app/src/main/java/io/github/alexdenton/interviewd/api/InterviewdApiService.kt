package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.CandidateDto
import io.github.alexdenton.interviewd.api.dto.InterviewDto
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.reactivex.Single
import retrofit2.http.*

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

    @DELETE("/questions/{id}")
    fun deleteQuestion(@Path("id") id: Int): Single<QuestionDto>

    @PATCH("/questions/{id}")
    fun patchQuestion(@Path("id") id: Int, @Body patch: QuestionDto): Single<QuestionDto>

    @GET("/templates")
    fun getTemplates(): Single<List<TemplateDto>>

    @GET("/templates/{id}")
    fun getTemplate(@Path("id") id: Int): Single<TemplateDto>

    @POST("/templates")
    fun createTemplate(@Body template: TemplateDto): Single<TemplateDto>

    @PATCH("/templates/{id}")
    fun patchTemplate(@Path("id") id: Int, @Body patch: TemplateDto): Single<TemplateDto>

    @GET("/candidates")
    fun getCandidates(): Single<List<CandidateDto>>

    @GET("/candidates/{id}")
    fun getCandidate(@Path("id") id: Int): Single<CandidateDto>

    @POST("/candidates")
    fun createCandidate(@Body candidate: CandidateDto): Single<CandidateDto>

    @PATCH("/candidates/{id}")
    fun patchCandidate(@Path("id") id: Int, @Body patch: CandidateDto): Single<CandidateDto>

    @GET("/interviews")
    fun getInterviews(): Single<List<InterviewDto>>

    @GET("/interviews/{id}")
    fun getInterview(@Path("id") id: Int): Single<InterviewDto>

    @POST("/interviews")
    fun createInterview(@Body interview: InterviewDto): Single<InterviewDto>

    @PATCH("/interviews/{id}")
    fun patchInterview(@Path("id") id: Int, @Body patch: InterviewDto): Single<InterviewDto>
}
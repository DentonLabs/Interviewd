package io.github.alexdenton.interviewd.retrofit

import io.github.alexdenton.interviewd.retrofit.dto.CandidateDto
import io.github.alexdenton.interviewd.retrofit.dto.InterviewDto
import io.github.alexdenton.interviewd.retrofit.dto.QuestionDto
import io.github.alexdenton.interviewd.retrofit.dto.TemplateDto
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by ryan on 11/14/17.
 */

interface InterviewdApiRetrofit {

    @GET("/questions")
    fun getQuestions(): Single<List<QuestionDto>>

    @GET("/questions/{id}")
    fun getQuestion(@Path("id") id: Long): Single<QuestionDto>

    @POST("/questions")
    fun createQuestion(@Body question: QuestionDto): Single<QuestionDto>

    @DELETE("/questions/{id}")
    fun deleteQuestion(@Path("id") id: Long): Single<QuestionDto>

    @PATCH("/questions/{id}")
    fun patchQuestion(@Path("id") id: Long, @Body patch: QuestionDto): Single<QuestionDto>

    @GET("/templates")
    fun getTemplates(): Single<List<TemplateDto>>

    @GET("/templates/{id}")
    fun getTemplate(@Path("id") id: Long): Single<TemplateDto>

    @POST("/templates")
    fun createTemplate(@Body template: TemplateDto): Single<TemplateDto>

    @DELETE("/templates/{id}")
    fun deleteTemplate(@Path("id") id: Long): Single<TemplateDto>

    @PATCH("/templates/{id}")
    fun patchTemplate(@Path("id") id: Long, @Body patch: TemplateDto): Single<TemplateDto>

    @GET("/candidates")
    fun getCandidates(): Single<List<CandidateDto>>

    @GET("/candidates/{id}")
    fun getCandidate(@Path("id") id: Long): Single<CandidateDto>

    @POST("/candidates")
    fun createCandidate(@Body candidate: CandidateDto): Single<CandidateDto>

    @DELETE("/candidates")
    fun deleteCandidate(@Path("id") id: Long): Single<CandidateDto>

    @PATCH("/candidates/{id}")
    fun patchCandidate(@Path("id") id: Long, @Body patch: CandidateDto): Single<CandidateDto>

    @GET("/interviews")
    fun getInterviews(): Single<List<InterviewDto>>

    @GET("/interviews/{id}")
    fun getInterview(@Path("id") id: Long): Single<InterviewDto>

    @POST("/interviews")
    fun createInterview(@Body interview: InterviewDto): Single<InterviewDto>

    @DELETE("/interviews/{id}")
    fun deleteInterview(@Path("id") id: Long): Single<InterviewDto>

    @PATCH("/interviews/{id}")
    fun patchInterview(@Path("id") id: Long, @Body patch: InterviewDto): Single<InterviewDto>

}
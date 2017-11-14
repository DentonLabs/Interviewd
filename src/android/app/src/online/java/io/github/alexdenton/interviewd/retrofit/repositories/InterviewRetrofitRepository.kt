package io.github.alexdenton.interviewd.retrofit.repositories

import android.content.Context
import io.github.alexdenton.interviewd.api.InterviewdApi
import io.github.alexdenton.interviewd.retrofit.RetrofitFactory
import io.github.alexdenton.interviewd.retrofit.dto.CandidateDto
import io.github.alexdenton.interviewd.retrofit.dto.InterviewDto
import io.github.alexdenton.interviewd.retrofit.dto.QuestionDto
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.retrofit.InterviewdApiRetrofit
import io.reactivex.Single

/**
 * Created by ryan on 9/9/17.
 */
class InterviewRetrofitRepository(val context: Context) : InterviewRepository {

    val client: InterviewdApiRetrofit = RetrofitFactory(context).create(RetrofitFactory.Mode.Local)

    override fun getInterview(id: Int): Single<Interview>
            = client.getInterview(id)
            .map { it.toInterview() }

    override fun getAllInterviews(): Single<List<Interview>>
            = client.getInterviews()
            .map { it.map { it.toInterview() } }

    override fun createInterview(interview: Interview): Single<Interview>
            = client.createInterview(interview.toDto())
            .map { it.toInterview() }

    override fun updateInterview(interview: Interview): Single<Interview>
            = client.patchInterview(interview.id, interview.toDto())
            .map { it.toInterview() }

    override fun deleteInterview(id: Int): Single<Interview>
            = client.deleteInterview(id)
            .map { it.toInterview() }


    fun Interview.toDto() = InterviewDto(id, candidate.toDto(), name, questions.map { it.toDto() }, status)

    fun Question.toDto() = QuestionDto(id, name, description, timeEstimate)

    fun Candidate.toDto() = CandidateDto(id, firstName, lastName)
}
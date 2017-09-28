package io.github.alexdenton.interviewd.api

import android.content.Context
import io.github.alexdenton.interviewd.api.dto.CandidateDto
import io.github.alexdenton.interviewd.api.dto.InterviewDto
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.interview.Candidate
import io.github.alexdenton.interviewd.interview.Interview
import io.github.alexdenton.interviewd.interview.InterviewStatus
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Single

/**
 * Created by ryan on 9/9/17.
 */
class InterviewRetrofitRepository(val context: Context) : InterviewRepository {

    val client: InterviewdApiService = RetrofitFactory(context).create(RetrofitFactory.Mode.Demo)

    override fun getInterview(id: Int): Single<Interview>
            = client.getInterview(id)
            .map { it.toInterview() }

    override fun getAllInterviews(): Single<List<Interview>>
            = client.getInterviews()
            .map { it.map { it.toInterview() } }

    override fun createInterview(interview: Interview): Single<Interview>
            = client.createInterview(interview.toDto())
            .map { it.toInterview() }

    override fun markInterviewAsComplete(interview: Interview): Single<Interview>
            = client.markInterviewAsComplete(interview.id)
            .map { it.toInterview() }


    fun Interview.toDto() = InterviewDto(id, candidate.toDto(), name, questions.map { it.toDto() }, status)

    fun Question.toDto() = QuestionDto(0, name, description, timeEstimate)

    fun Candidate.toDto() = CandidateDto(id, firstName, lastName)
}
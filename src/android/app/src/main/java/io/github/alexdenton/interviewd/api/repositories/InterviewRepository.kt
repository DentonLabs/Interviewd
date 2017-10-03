package io.github.alexdenton.interviewd.api.repositories

import io.github.alexdenton.interviewd.entities.Interview
import io.reactivex.Single

/**
 * Created by ryan on 9/9/17.
 */
interface InterviewRepository {
    fun getInterview(id: Int): Single<Interview>
    fun getAllInterviews(): Single<List<Interview>>
    fun createInterview(interview: Interview): Single<Interview>
    fun markInterviewAsComplete(interview: Interview): Single<Interview>
}
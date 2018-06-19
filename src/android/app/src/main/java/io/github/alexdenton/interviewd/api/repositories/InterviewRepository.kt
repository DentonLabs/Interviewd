package io.github.alexdenton.interviewd.api.repositories

import io.github.alexdenton.interviewd.entities.Interview
import io.reactivex.Single

/**
 * Created by ryan on 9/9/17.
 */
interface InterviewRepository {
    fun getInterview(id: Long): Single<Interview>
    fun getAllInterviews(): Single<List<Interview>>
    fun createInterview(interview: Interview): Single<Interview>
    fun updateInterview(interview: Interview): Single<Interview>
    fun deleteInterview(id: Long): Single<Interview>
}
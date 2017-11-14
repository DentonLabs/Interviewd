package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class InterviewObjectboxRepository(client: InterviewdObjectboxApi) : InterviewRepository {
    override fun getInterview(id: Long): Single<Interview> {
        return Single.never()
    }

    override fun getAllInterviews(): Single<List<Interview>> {
        return Single.never()
    }

    override fun createInterview(interview: Interview): Single<Interview> {
        return Single.never()
    }

    override fun updateInterview(interview: Interview): Single<Interview> {
        return Single.never()
    }

    override fun deleteInterview(id: Long): Single<Interview> {
        return Single.never()
    }
}
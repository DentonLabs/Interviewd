package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class InterviewObjectboxRepository(val client: InterviewdObjectboxApi) : InterviewRepository {
    override fun getInterview(id: Long): Single<Interview> {
        return client.getInterview(id).map { it.toInterview() }
    }

    override fun getAllInterviews(): Single<List<Interview>> {
        return client.getInterviews().map { it.map { it.toInterview() } }
    }

    override fun createInterview(interview: Interview): Single<Interview> {
        return client.createInterview(interview.toEntity())
                .map { it.toInterview() }
    }

    override fun updateInterview(interview: Interview): Single<Interview> {
        return client.getInterview(interview.id)
                .flatMap { client.patchInterview(it.id, it.updateFrom(interview)) }
                .map { it.toInterview() }
    }

    override fun deleteInterview(id: Long): Single<Interview> {
        return client.deleteInterview(id)
                .map { it.toInterview() }
    }
}
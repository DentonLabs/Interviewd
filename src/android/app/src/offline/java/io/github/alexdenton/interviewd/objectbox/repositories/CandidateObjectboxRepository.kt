package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class CandidateObjectboxRepository(client: InterviewdObjectboxApi) : CandidateRepository {
    override fun getCandidate(id: Int): Single<Candidate> {
        return Single.never()
    }

    override fun getAllCandidates(): Single<List<Candidate>> {
        return Single.never()
    }

    override fun createCandidate(candidate: Candidate): Single<Candidate> {
        return Single.never()
    }

    override fun updateCandidate(candidate: Candidate): Single<Candidate> {
        return Single.never()
    }

    override fun deleteCandidate(id: Int): Single<Candidate> {
        return Single.never()
    }
}
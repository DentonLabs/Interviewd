package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class CandidateObjectboxRepository(val client: InterviewdObjectboxApi) : CandidateRepository {
    override fun getCandidate(id: Long): Single<Candidate> {
        return client.getCandidate(id).map { it.toCandidate() }
    }

    override fun getAllCandidates(): Single<List<Candidate>> {
        return client.getCandidates().map { it.map { it.toCandidate() } }
    }

    override fun createCandidate(candidate: Candidate): Single<Candidate> {
        return client.createCandidate(candidate.toEntity()).map { it.toCandidate() }
    }

    override fun updateCandidate(candidate: Candidate): Single<Candidate> {
        return client.patchCandidate(candidate.id, candidate.toEntity()).map { it.toCandidate() }
    }

    override fun deleteCandidate(id: Long): Single<Candidate> {
        return client.deleteCandidate(id).map { it.toCandidate() }
    }

}
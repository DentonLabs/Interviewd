package io.github.alexdenton.interviewd.api.repositories

import io.github.alexdenton.interviewd.entities.Candidate
import io.reactivex.Single

/**
 * Created by ryan on 8/28/17.
 */
interface CandidateRepository {
    fun getCandidate(id: Int): Single<Candidate>
    fun getAllCandidates(): Single<List<Candidate>>
    fun createCandidate(candidate: Candidate): Single<Candidate>
    fun updateCandidate(candidate: Candidate): Single<Candidate>
}
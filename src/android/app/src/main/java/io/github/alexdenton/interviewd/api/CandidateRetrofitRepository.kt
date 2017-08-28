package io.github.alexdenton.interviewd.api

import android.content.Context
import io.github.alexdenton.interviewd.api.dto.CandidateDto
import io.github.alexdenton.interviewd.interview.Candidate
import io.reactivex.Single

/**
 * Created by ryan on 8/28/17.
 */
class CandidateRetrofitRepository(val context: Context) : CandidateRepository {

    val client: InterviewdApiService = RetrofitFactory(context).create(RetrofitFactory.Mode.Demo)

    override fun getCandidate(id: Int): Single<Candidate>
            = client.getCandidate(id)
            .map { it.toCandidate() }

    override fun getAllCandidates(): Single<List<Candidate>>
            = client.getCandidates()
            .map { it.map { it.toCandidate() } }

    override fun createCandidate(candidate: Candidate): Single<Candidate>
            = client.createCandidate(candidate.toDto())
            .map { it.toCandidate() }


    fun Candidate.toDto() = CandidateDto(id, firstName, lastName)
}
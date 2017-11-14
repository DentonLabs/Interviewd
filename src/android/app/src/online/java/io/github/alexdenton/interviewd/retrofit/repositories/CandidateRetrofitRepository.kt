package io.github.alexdenton.interviewd.retrofit.repositories

import android.content.Context
import io.github.alexdenton.interviewd.api.InterviewdApi
import io.github.alexdenton.interviewd.retrofit.RetrofitFactory
import io.github.alexdenton.interviewd.retrofit.dto.CandidateDto
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.retrofit.InterviewdApiRetrofit
import io.reactivex.Single

/**
 * Created by ryan on 8/28/17.
 */
class CandidateRetrofitRepository(val context: Context) : CandidateRepository {

    val client: InterviewdApiRetrofit = RetrofitFactory(context).create(RetrofitFactory.Mode.Local)

    override fun getCandidate(id: Int): Single<Candidate>
            = client.getCandidate(id)
            .map { it.toCandidate() }

    override fun getAllCandidates(): Single<List<Candidate>>
            = client.getCandidates()
            .map { it.map { it.toCandidate() } }

    override fun createCandidate(candidate: Candidate): Single<Candidate>
            = client.createCandidate(candidate.toDto())
            .map { it.toCandidate() }

    override fun updateCandidate(candidate: Candidate): Single<Candidate>
            = client.patchCandidate(candidate.id, candidate.toDto())
            .map { it.toCandidate() }

    override fun deleteCandidate(id: Int): Single<Candidate>
            = client.deleteCandidate(id)
            .map { it.toCandidate() }

    fun Candidate.toDto() = CandidateDto(id, firstName, lastName)
}
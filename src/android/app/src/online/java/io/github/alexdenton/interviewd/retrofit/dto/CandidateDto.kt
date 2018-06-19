package io.github.alexdenton.interviewd.retrofit.dto

import io.github.alexdenton.interviewd.entities.Candidate

/**
 * Created by ryan on 8/28/17.
 */
class CandidateDto(
        val id: Long,
        val firstName: String,
        val lastName: String
){
    fun toCandidate() = Candidate(id, firstName, lastName)
}
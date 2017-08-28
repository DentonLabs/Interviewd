package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.interview.Candidate

/**
 * Created by ryan on 8/28/17.
 */
class CandidateDto(
        val id: Int,
        val firstName: String,
        val lastName: String
){
    fun toCandidate() = Candidate(id, firstName, lastName)
}
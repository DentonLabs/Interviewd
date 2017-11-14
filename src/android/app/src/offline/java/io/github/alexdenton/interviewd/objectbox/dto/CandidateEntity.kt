package io.github.alexdenton.interviewd.objectbox.dto

import io.github.alexdenton.interviewd.entities.Candidate
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by ryan on 11/14/17.
 */

@Entity
data class CandidateEntity(
        @Id var id: Long,
        val firstName: String,
        val lastName: String
){
    fun toCandidate() = Candidate(id, firstName, lastName)
}
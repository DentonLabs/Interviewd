package io.github.alexdenton.interviewd.entities

/**
 * Created by ryan on 7/24/17.
 */
data class Candidate (
        val id: Long,
        val firstName: String,
        val lastName: String
){
    override fun toString(): String = firstName + " " + lastName
}
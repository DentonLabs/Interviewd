package io.github.alexdenton.interviewd.entities

/**
 * Created by ryan on 8/10/17.
 */

data class Template(
        val name: String,
        val questions: List<Question>,
        val id: Long = 0
){
    override fun toString(): String = name
}
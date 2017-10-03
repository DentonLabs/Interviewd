package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.entities.InterviewStatus

/**
 * Created by ryan on 9/9/17.
 */
data class InterviewDto (
        val id: Int,
        val candidate: CandidateDto,
        val name: String,
        val questions: List<QuestionDto>,
        var status: InterviewStatus
){
    fun toInterview() = Interview(id, candidate.toCandidate(), name, questions.map { it.toQuestion() }, status)
}
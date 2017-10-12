package io.github.alexdenton.interviewd.candidate.detail

/**
 * Created by ryan on 10/12/17.
 */
sealed class CandidateDetailEvent

object ShowCandidateDetailFragment : CandidateDetailEvent()
object ShowCandidateFormFragment : CandidateDetailEvent()

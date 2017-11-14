package io.github.alexdenton.interviewd.objectbox.dto

import io.objectbox.annotation.Id

/**
 * Created by ryan on 11/14/17.
 */
data class CandidateEntity(
        @Id var id: Long
)
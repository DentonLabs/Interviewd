package io.github.alexdenton.interviewd.objectbox.dto

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by ryan on 11/14/17.
 */

@Entity
data class InterviewEntity(
        @Id var id: Long
)
package io.github.alexdenton.interviewd.api.dto

import io.github.alexdenton.interviewd.interview.Template

/**
 * Created by ryan on 8/14/17.
 */
data class TemplateDto (val name: String, val questionIds: List<Int>, val id: Int = 0){
}
package io.github.alexdenton.interviewd.api.dto

/**
 * Created by ryan on 8/14/17.
 */
data class TemplateDto (val name: String, val questionIds: List<Int>, val id: Int = 0){
}
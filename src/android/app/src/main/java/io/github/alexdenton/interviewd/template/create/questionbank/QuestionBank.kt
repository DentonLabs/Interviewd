package io.github.alexdenton.interviewd.template.create.questionbank

import android.support.v4.app.FragmentManager
import io.github.alexdenton.interviewd.entities.Question

/**
 * Created by ryan on 10/14/17.
 */
interface QuestionBank {
    fun useCheckedQuestions(checkedQuestions: List<Question>)
    fun getCheckedQuestions(): List<Question>
}
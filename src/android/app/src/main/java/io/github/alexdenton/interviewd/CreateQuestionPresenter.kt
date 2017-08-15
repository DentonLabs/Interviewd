package io.github.alexdenton.interviewd

import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.question.QuestionSubmitImpl

class CreateQuestionPresenter(val activity: CreateQuestionActivity) {

    val questionSubmit = QuestionSubmitImpl()

    fun submitQuestion() = questionSubmit.submit(getQuestionFromFields())

    private fun getQuestionFromFields(): Question =
            Question(0, activity.nameField.text.toString(), activity.descField.text.toString(), activity.durField.text.toString().toInt())


    
}
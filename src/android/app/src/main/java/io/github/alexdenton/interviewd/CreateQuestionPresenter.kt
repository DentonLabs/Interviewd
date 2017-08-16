package io.github.alexdenton.interviewd

import android.widget.Toast
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateQuestionPresenter(val activity: CreateQuestionActivity, val repo: QuestionRepository) {


    fun submitQuestion() = repo.createQuestion(getQuestionFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> submitSuccess()}


    fun submitSuccess() {
        Toast.makeText(activity.applicationContext, "Successfully sent", Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun getQuestionFromFields(): Question =
            Question(0, activity.nameField.text.toString(), activity.descField.text.toString(), activity.durField.text.toString().toInt())

    private fun clearFields() {
        activity.nameField.text.clear()
        activity.descField.text.clear()
        activity.durField.text.clear()
    }

}
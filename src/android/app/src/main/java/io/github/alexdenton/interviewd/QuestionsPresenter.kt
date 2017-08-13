package io.github.alexdenton.interviewd

import android.util.Log
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/12/17.
 */
class QuestionsPresenter(val repo: QuestionRepository, val fragment: QuestionsFragment) {

    fun getAllQuestions() = repo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> foundQuestions(list) },
                    { throwable -> couldNotConnect(throwable) })

    fun foundQuestions(list: List<Question>) {
        fragment.onFoundQuestions(list)
    }

    fun couldNotConnect(throwable: Throwable) {
        Log.w("Network", throwable)
        fragment.onCouldNotConnect()
    }
}
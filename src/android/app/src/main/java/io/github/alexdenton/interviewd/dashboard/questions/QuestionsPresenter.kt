package io.github.alexdenton.interviewd.dashboard.questions

import android.util.Log
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/12/17.
 */
class QuestionsPresenter(val repo: QuestionRepository, val fragment: QuestionsFragment) {

    fun getAllQuestions(): Disposable = repo.getAllQuestions()
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
package io.github.alexdenton.interviewd

import android.util.Log
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendToQuestionBankEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/24/17.
 */
class QuestionBankPresenter(val repo: QuestionRepository, val activity: QuestionBankActivity) {

    val disposables: CompositeDisposable = CompositeDisposable()

    fun getAllQuestions() = disposables.add(repo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> foundQuestions(list) },
                    { throwable -> couldNotConnect(throwable) }))

    fun getCheckedQuestions() = RxBus.toObservable(SendToQuestionBankEvent::class.java)
            .subscribe({ (questionBank) -> setCheckedQuestions(questionBank) },
                    { throwable -> Log.w("Bork", throwable) })

    private fun setCheckedQuestions(questionBank: List<Question>) {
        activity.alreadyChecked = questionBank
    }

    fun foundQuestions(list: List<Question>) =
            activity.setUpQuestions(list)

    fun couldNotConnect(throwable: Throwable?) {
        Log.w("Network", throwable)
        activity.onCouldNotConnect()
    }

    fun disposeAll() = disposables.dispose()

}
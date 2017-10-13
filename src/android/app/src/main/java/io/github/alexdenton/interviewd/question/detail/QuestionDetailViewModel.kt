package io.github.alexdenton.interviewd.question.detail

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/29/17.
 */
class QuestionDetailViewModel : RxAwareViewModel() {

    private lateinit var questionRepo: QuestionRepository
    private val question: BehaviorRelay<Question> = BehaviorRelay.create()
    fun getQuestionObservable(): Observable<Question> = question

    fun init(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
    }

    fun getQuestion(id: Int): Single<Question> = questionRepo.getQuestion(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}
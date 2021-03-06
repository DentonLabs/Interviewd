package io.github.alexdenton.interviewd.question

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/23/17.
 */
class QuestionFormViewModel : RxAwareViewModel() {

    private lateinit var questionRepo: QuestionRepository
    var editing = false
    var id: Long = 0

    fun withKodein(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
    }

    fun submitQuestion(question: Question) = when(editing) {
        true -> updateQuestion(question)
        false -> createQuestion(question)
    }

    fun createQuestion(question: Question) = questionRepo.createQuestion(question)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateQuestion(question: Question) = questionRepo.updateQuestion(question)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchQuestion() = questionRepo.getQuestion(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
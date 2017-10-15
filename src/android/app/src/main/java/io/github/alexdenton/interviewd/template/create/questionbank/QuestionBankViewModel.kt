package io.github.alexdenton.interviewd.template.create.questionbank

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 10/13/17.
 */
class QuestionBankViewModel : RxAwareViewModel() {

    lateinit var questionRepo: QuestionRepository

    fun initWith(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
    }

    fun fetchAllQuestions() = questionRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
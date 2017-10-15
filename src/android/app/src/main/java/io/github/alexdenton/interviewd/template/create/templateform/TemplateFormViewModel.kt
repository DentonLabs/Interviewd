package io.github.alexdenton.interviewd.template.create.templateform

import com.github.salomonbrys.kodein.*
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.rfonzi.rxaware.bus.UIBus
import io.github.alexdenton.interviewd.template.create.questionbank.QuestionBankFragment
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/19/17.
 */
class TemplateFormViewModel : RxAwareViewModel() {

    lateinit var questionRepo: QuestionRepository
    lateinit var templateRepo: TemplateRepository

    fun withKodein(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
        templateRepo = kodein.invoke().instance()
    }

    fun submitTemplate(template: Template) = templateRepo.createTemplate(template)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}

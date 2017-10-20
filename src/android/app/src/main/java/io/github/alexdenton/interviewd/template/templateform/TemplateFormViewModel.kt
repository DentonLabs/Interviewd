package io.github.alexdenton.interviewd.template.templateform

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.entities.Template
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/19/17.
 */
class TemplateFormViewModel : RxAwareViewModel() {

    var editing = false
    var templateId = 0

    lateinit var questionRepo: QuestionRepository
    lateinit var templateRepo: TemplateRepository

    private val chosenQuestions = BehaviorRelay.createDefault<List<Question>>(listOf())

    fun withKodein(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
        templateRepo = kodein.invoke().instance()
    }

    fun submitTemplate(template: Template) = when(editing){
        true -> updateTemplate(template)
        false -> addTemplate(template)
    }

    fun addTemplate(template: Template) = templateRepo.createTemplate(template)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateTemplate(template: Template) = templateRepo.updateTemplate(template)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchTemplate(id: Int) = templateRepo.getTemplate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getChosenQuestions(): Observable<List<Question>> = (chosenQuestions as Observable<List<Question>>)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun useChosenQuestions(questions: List<Question>) = chosenQuestions.accept(questions)

}

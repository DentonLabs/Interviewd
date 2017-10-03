package io.github.alexdenton.interviewd.detailtemplate

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormAdapter
import io.github.alexdenton.interviewd.detailquestion.QuestionDetailSignal
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 10/2/17.
 */
class TemplateDetailViewModel : RxAwareViewModel() {

    lateinit var templatesRepo: TemplateRepository
    lateinit var questionsRepo: QuestionRepository
    private val template: BehaviorRelay<Template> = BehaviorRelay.create()
    private val allQuestions: PublishRelay<List<Question>> = PublishRelay.create()
    fun getTemplateObservable(): Observable<Template> = template
    fun getAllQuestionsObservable(): Observable<List<Question>> = allQuestions

    var editedName = ""
    var editedQuestions = mutableListOf<Question>()

    fun initWith(kodein: LazyKodein) {
        templatesRepo = kodein.invoke().instance()
        questionsRepo = kodein.invoke().instance()
    }

    fun use(id: Int) = templatesRepo.getTemplate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> template.accept(result) }
            .lifecycleAware()

    fun submitEdit() = templatesRepo.updateTemplate(Template(editedName, editedQuestions, template.value.id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { success -> onSubmit(success) }
            .lifecycleAware()

    fun fetchAllQuestions() = questionsRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { success -> allQuestions.accept(success) }
            .lifecycleAware()

    fun beginEditingTemplate() = fragmentTransaction {
        replace(R.id.templateDetail_fragmentContainer, TemplateDetailEditFragment())
    }

    fun stopEditingTemplate() = fragmentTransaction {
        replace(R.id.templateDetail_fragmentContainer, TemplateDetailShowFragment())
    }

    fun exposeNameChanges(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { editedName = it.toString() }
            .lifecycleAware()

    fun exposeQuestionChanges(dataChanges: InitialValueObservable<TemplateFormAdapter>) = dataChanges
            .subscribe {
                editedQuestions.clear()
                editedQuestions.addAll(it.bankedQuestions)
            }
            .lifecycleAware()

    private fun onSubmit(template: Template) {
        this.template.accept(template)
        stopEditingTemplate()
    }

}
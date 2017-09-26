package io.github.alexdenton.interviewd.createinterview

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.CandidateRepository
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/25/17.
 */
class CreateInterviewViewModel : BaseViewModel() {

    lateinit var questionRepo: QuestionRepository
    lateinit var candidateRepo: CandidateRepository
    lateinit var templateRepo: TemplateRepository

    private val templates: PublishRelay<List<Template>> = PublishRelay.create()
    private val allQuestions: PublishRelay<List<Question>> = PublishRelay.create()
    private val selectedQuestions: BehaviorRelay<List<Question>> = BehaviorRelay.createDefault(listOf())

    private val templateDialogSignal: PublishRelay<DialogSignal> = PublishRelay.create()
    private val questionDialogSignal: PublishRelay<DialogSignal> = PublishRelay.create()

    fun getTemplatesObservable(): Observable<List<Template>> = templates
    fun getAllQuestionsObservable(): Observable<List<Question>> = allQuestions
    fun getSelectedQuestionsObservable(): Observable<List<Question>> = selectedQuestions
    fun getTemplateDialogSignal(): Observable<DialogSignal> = templateDialogSignal
    fun getQuestionDialogSignal(): Observable<DialogSignal> = questionDialogSignal

    fun initWith(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
        candidateRepo = kodein.invoke().instance()
        templateRepo = kodein.invoke().instance()
    }

    fun fetchTemplates() = templateRepo.getAllTemplates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> templates.accept(list) },
                    { throwable -> throwable.printStackTrace() })

    fun fetchQuestions() = questionRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> allQuestions.accept(list) },
                    { throwable -> throwable.printStackTrace() })

    fun exposeLoadTemplateButton(clicks: Observable<Unit>) = clicks
            .subscribe {
                fetchTemplates()
                templateDialogSignal.accept(DialogSignal.SHOW)
            }

    fun exposeLoadTemplateSelections(itemClicked: Observable<Template>) = itemClicked
            .subscribe { template ->
                selectedQuestions.accept(template.questions)
                templateDialogSignal.accept(DialogSignal.HIDE)
            }

    fun exposeAddQuestionButton(clicks: Observable<Unit>) = clicks
            .subscribe {
                fetchQuestions()
                questionDialogSignal.accept(DialogSignal.SHOW)
            }

    fun acceptQuestions(checkedQuestions: MutableList<Question>) = selectedQuestions.accept(checkedQuestions)

}

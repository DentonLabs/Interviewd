package io.github.alexdenton.interviewd.template.create

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
class CreateTemplateViewModel : RxAwareViewModel() {

    lateinit var questionRepo: QuestionRepository
    lateinit var templateRepo: TemplateRepository

    val chosenQuestions: BehaviorRelay<List<Question>> = BehaviorRelay.createDefault(listOf())
    val allQuestions: BehaviorRelay<List<Question>> = BehaviorRelay.createDefault(listOf())
    private val nameField: BehaviorRelay<String> = BehaviorRelay.createDefault("")

    fun withKodein(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
        templateRepo = kodein.invoke().instance()
    }

    private fun updateChosenQuestions(questions: List<Question>) = chosenQuestions.accept(questions)


    fun fetchAllQuestions() = questionRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> allQuestions.accept(list) },
                    { throwable -> throwable.printStackTrace() })
            .lifecycleAware()

    fun submitTemplate() = templateRepo.createTemplate(getTemplateSnapshot())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> submitSuccess() }
            .lifecycleAware()

    fun exposeSubmitButton(actions: Observable<Unit>): Boolean = actions
            .subscribe {
                submitTemplate()
            }
            .lifecycleAware()

    fun exposeNameField(actions: Observable<CharSequence>): Boolean = actions
            .subscribe { nameField.accept(it.toString()) }
            .lifecycleAware()

    fun exposeAddQuestionButton(clicks: Observable<Unit>): Boolean = clicks
            .subscribe {
                UIBus.fragmentTransaction {
                    replace(R.id.createTemplate_fragmentContainer, QuestionBankFragment())
                    addToBackStack(null)
                }
            }
            .lifecycleAware()

    fun getTemplateSnapshot() = Template(nameField.value, chosenQuestions.value)

    private fun submitSuccess() {
        UIBus.toast("Template submitted!")
        UIBus.navigateUp()
    }

    fun exposeChosenQuestions(list: Observable<List<Question>>) = list
            .subscribe {
                chosenQuestions.accept(it)
            }
            .lifecycleAware()
}

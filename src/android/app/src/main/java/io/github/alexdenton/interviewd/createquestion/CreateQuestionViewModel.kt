package io.github.alexdenton.interviewd.createquestion

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ryan on 9/23/17.
 */
class CreateQuestionViewModel : RxAwareViewModel() {

    private lateinit var questionRepo: QuestionRepository

    val clearSignal: PublishRelay<Any> = PublishRelay.create()

    var name = ""
    var estimate = 0
    var description = ""

    fun withKodein(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
    }

    fun exposeNameField(entry: Observable<String>) = entry
            .subscribe { name = it }
            .lifecycleAware()

    fun exposeEstimateField(entry: Observable<Int>) = entry
            .subscribe { estimate = it }
            .lifecycleAware()

    fun exposeDescField(entry: Observable<String>) = entry
            .subscribe { description = it }
            .lifecycleAware()

    fun submitQuestion() = questionRepo.createQuestion(getQuestionFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ submitSuccess() },
                    { throwable -> throwable.printStackTrace() })
            .lifecycleAware()

    private fun getQuestionFromFields(): Question {
        return Question(0, name, description, estimate)
    }

    private fun submitSuccess() {
        toast("Successfully sent")
        clearSignal.accept("clear") // We care about the signal itself, not the value in it
    }
}
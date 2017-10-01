package io.github.alexdenton.interviewd.detailquestion

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/29/17.
 */
class QuestionDetailViewModel : RxAwareViewModel() {

    private lateinit var questionRepo: QuestionRepository
    private val question: BehaviorRelay<Question> = BehaviorRelay.create()
    fun getQuestionObservable(): Observable<Question> = question

    var editedName = ""
    var editedDesc = ""
    var editedEst = 0

    fun init(kodein: LazyKodein, questionId: Int) {
        questionRepo = kodein.invoke().instance()
        getQuestion(questionId)
    }

    private fun getQuestion(id: Int) = questionRepo.getQuestion(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ get -> question.accept(get) },
                    { throwable -> throwable.printStackTrace() })

    private fun updateQuestion(updatedQuestion: Question) = questionRepo.updateQuestion(updatedQuestion)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ patchedQuestion -> finishEditingQuestion(patchedQuestion)},
                    { throwable -> throwable.printStackTrace() })

    fun startEditingQuestion() = switchToEditFragment()

    fun cancelEditingQuestion() = switchToShowFragment()

    fun submitEdit() {
        val updatedQuestion = Question(question.value.id, editedName, editedDesc, editedEst)
        updateQuestion(updatedQuestion)
    }

    fun finishEditingQuestion(patchedQuestion: Question){
        question.accept(patchedQuestion)
        toast("Edit successful")
        switchToShowFragment()
    }

    fun exposeNameEdits(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { editedName = it.toString() }

    fun exposeDescEdits(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { editedDesc = it.toString() }

    fun exposeEstEdits(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { editedEst = it.toString().toIntOrNull() ?: 0 }

    fun switchToEditFragment() = fragmentTransaction {
        replace(R.id.questionDetail_fragmentContainer, QuestionDetailEditFragment())
    }

    fun switchToShowFragment() = fragmentTransaction {
        replace(R.id.questionDetail_fragmentContainer, QuestionDetailShowFragment())
    }
}
package io.github.alexdenton.interviewd.createtemplate.templateform

import android.util.Log
import android.widget.Toast
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendToTemplateFormEvent
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/18/17.
 */
class TemplateFormPresenter(val fragment: TemplateFormFragment, val repo: TemplateRepository) {

    val questionsFromBank: MutableList<Question> = emptyList<Question>().toMutableList()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun submitTemplate() = disposables.add(repo.createTemplate(getTemplateFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> submitSuccess() })

    fun updatePickedQuestions() = disposables.add(RxBus.toObservable(SendToTemplateFormEvent::class.java)
            .subscribe({ updateSuccess(it.list) },
                    { onError(it) }))

    private fun updateSuccess(list: List<Question>) {
        questionsFromBank.apply {
            clear()
            addAll(list)
        }

        fragment.onUpdateSuccess()
    }

    private fun submitSuccess() {
        Toast.makeText(fragment.context, "Submitted template!", Toast.LENGTH_SHORT).show()
        fragment.onSubmitSuccess()
    }

    private fun getTemplateFromFields(): Template
            = Template(fragment.titleField.text.toString(), questionsFromBank)

    fun startAddingQuestions() = fragment.switchToQuestionBank(questionsFromBank)

    private fun onError(throwable: Throwable) = Log.w("Error", throwable)
    fun disposeAll() = disposables.dispose()


}
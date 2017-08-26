package io.github.alexdenton.interviewd

import android.util.Log
import android.widget.Toast
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendToCreateTemplateEvent
import io.github.alexdenton.interviewd.bus.events.SendToQuestionBankEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/18/17.
 */
class CreateTemplatePresenter(val activity: CreateTemplateActivity, val repo: TemplateRepository) {

    val questionsFromBank: MutableList<Question> = emptyList<Question>().toMutableList()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun submitTemplate() = disposables.add(repo.createTemplate(getTemplateFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> submitSuccess() })

    fun updatePickedQuestions() = RxBus.getEvents(SendToCreateTemplateEvent::class.java)
            .subscribe({ updateSuccess(it.list) },
                    { onError(it) })

    private fun updateSuccess(list: List<Question>) {
        questionsFromBank.apply {
            clear()
            addAll(list)
        }

        activity.onUpdateSuccess()
    }

    private fun submitSuccess() {
        Toast.makeText(activity, "Submitted template!", Toast.LENGTH_SHORT).show()
        activity.onSubmitSuccess()
    }

    private fun getTemplateFromFields(): Template
            = Template(activity.titleField.text.toString(), questionsFromBank)

    fun startAddingQuestions() = activity.switchToQuestionBank(questionsFromBank)

    private fun onError(throwable: Throwable) = Log.w("Error", throwable)
    fun disposeAll() = disposables.dispose()


}
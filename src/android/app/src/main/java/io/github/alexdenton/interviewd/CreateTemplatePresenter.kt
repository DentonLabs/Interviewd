package io.github.alexdenton.interviewd

import android.widget.Toast
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/18/17.
 */
class CreateTemplatePresenter(val activity: CreateTemplateActivity, val repo: TemplateRepository) {

    val questionsFromBank: MutableList<Question> = emptyList<Question>().toMutableList()

    fun submitTemplate() = repo.createTemplate(getTemplateFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> submitSuccess() }

    private fun submitSuccess() {
        Toast.makeText(activity, "Submitted template!", Toast.LENGTH_SHORT).show()
        activity.onSubmitSuccess()
    }

    private fun getTemplateFromFields(): Template
            = Template(activity.titleField.text.toString(), questionsFromBank)


}
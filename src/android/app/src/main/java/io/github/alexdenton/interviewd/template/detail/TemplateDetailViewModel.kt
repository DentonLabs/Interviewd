package io.github.alexdenton.interviewd.template.detail

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 10/2/17.
 */
class TemplateDetailViewModel : RxAwareViewModel() {

    var id = 0

    lateinit var templatesRepo: TemplateRepository
    lateinit var questionsRepo: QuestionRepository

    fun initWith(kodein: LazyKodein) {
        templatesRepo = kodein.invoke().instance()
        questionsRepo = kodein.invoke().instance()
    }

    fun useId(id: Int){ this.id = id }

    fun fetchTemplate(id: Int) = templatesRepo.getTemplate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteTemplate() = templatesRepo.deleteTemplate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
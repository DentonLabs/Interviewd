package io.github.alexdenton.interviewd.dashboard.templates

import android.util.Log
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.interview.Template
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/14/17.
 */
class TemplatesPresenter(val repo: TemplateRepository, val fragment: TemplatesFragment) {

    fun getAllTemplates() = repo.getAllTemplates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> foundTemplates(list) },
                    { throwable -> couldNotConnect(throwable) })

    fun foundTemplates(list: List<Template>) = fragment.onFoundTemplates(list)

    fun couldNotConnect(throwable: Throwable) {
        Log.w("Network", throwable)
        fragment.onCouldNotConnect()
    }
}
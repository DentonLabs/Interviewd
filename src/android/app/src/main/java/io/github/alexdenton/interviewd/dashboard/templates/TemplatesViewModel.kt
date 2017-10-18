package io.github.alexdenton.interviewd.dashboard.templates

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.template.detail.TemplateDetailActivity
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.template.create.templateform.AddEditTemplateActivity
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/25/17.
 */
class TemplatesViewModel : RxAwareViewModel() {

    lateinit var templateRepo: TemplateRepository
    private val templates: PublishRelay<List<Template>> = PublishRelay.create()

    fun getTemplatesObservable(): Observable<List<Template>> = templates

    fun initWith(kodein: LazyKodein) {
        templateRepo = kodein.invoke().instance()
        fetchTemplates()
    }

    fun fetchTemplates() = templateRepo.getAllTemplates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> templates.accept(list) },
                    { throwable -> throwable.printStackTrace() })
            .lifecycleAware()

    fun exposeAddFab(clicks: Observable<Unit>) = clicks
            .subscribe { startActivity(AddEditTemplateActivity::class.java) }

    fun exposeItemClicks(itemClicks: Observable<Template>) = itemClicks
            .subscribe { startActivityAndStore(TemplateDetailActivity::class.java, it.id) }
}
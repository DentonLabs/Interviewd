package io.github.alexdenton.interviewd.dashboard.interviews

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.interview.conduct.ConductInterviewActivity
import io.github.alexdenton.interviewd.interview.addedit.AddEditInterviewActivity
import io.github.alexdenton.interviewd.entities.Interview
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/25/17.
 */
class InterviewsViewModel : RxAwareViewModel() {

    private lateinit var interviewRepo: InterviewRepository
    private val interviews: PublishRelay<List<Interview>> = PublishRelay.create()

    fun getInterviewsObservable(): Observable<List<Interview>> = interviews

    fun initWith(kodein: LazyKodein) {
        interviewRepo = kodein.invoke().instance()
        fetchInterviews()
    }

    fun fetchInterviews() = interviewRepo.getAllInterviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> interviews.accept(list) },
                    { throwable -> throwable.printStackTrace() })
            .lifecycleAware()

    fun exposeAddFab(clicks: Observable<Unit>) = clicks
            .subscribe { startActivity(AddEditInterviewActivity::class.java) }

    fun exposeItemClicks(itemClicks: Observable<Interview>) = itemClicks
            .subscribe { startActivityAndStore(ConductInterviewActivity::class.java, it) }
}
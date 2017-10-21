package io.github.alexdenton.interviewd.interview.conduct

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.entities.Interview
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/27/17.
 */
class ConductInterviewViewModel : RxAwareViewModel() {

    private lateinit var interviewRepo: InterviewRepository


    var interviewId = 0
    var candidateId = 0
    var currentPage = 0
    var inProgress = false
    private val nextQuestionString: PublishRelay<String> = PublishRelay.create()
    private val startSignal: PublishRelay<InterviewSignal> = PublishRelay.create()
    private val nextPageSignal: PublishRelay<Int> = PublishRelay.create()

    fun getNextQuestionStringObservable(): Observable<String> = nextQuestionString
    fun getNextPageSignal(): Observable<Int> = nextPageSignal
    fun getStartSignal(): Observable<InterviewSignal> = startSignal

    fun initWith(kodein: LazyKodein){
        interviewRepo = kodein.invoke().instance()
    }

    fun useId(id: Int) { interviewId = id }

    fun fetchInterview(id: Int) = interviewRepo.getInterview(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteInterview() = interviewRepo.deleteInterview(interviewId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun exposeCurrentPage(pageSelections: Observable<Int>) = pageSelections
            .subscribe {

            }
            .lifecycleAware()

    fun exposeNextClicks(clicks: Observable<Unit>) = clicks
            .subscribe {

            }
            .lifecycleAware()

    fun exposeStartClicks(clicks: Observable<Unit>) = clicks
            .subscribe {
                startSignal.accept(InterviewSignal.START)
                inProgress = true
            }
            .lifecycleAware()
}
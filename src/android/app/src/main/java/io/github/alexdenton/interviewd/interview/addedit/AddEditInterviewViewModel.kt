package io.github.alexdenton.interviewd.interview.addedit

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.entities.Interview
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/25/17.
 */
class AddEditInterviewViewModel : RxAwareViewModel() {

    var editing = false
    var interviewId: Long = 0
    var candidateId: Long = 0

    lateinit var questionRepo: QuestionRepository
    lateinit var candidateRepo: CandidateRepository
    lateinit var templateRepo: TemplateRepository
    lateinit var interviewRepo: InterviewRepository

    fun initWith(kodein: LazyKodein) {
        questionRepo = kodein.invoke().instance()
        candidateRepo = kodein.invoke().instance()
        templateRepo = kodein.invoke().instance()
        interviewRepo = kodein.invoke().instance()
    }

    fun fetchTemplates() = templateRepo.getAllTemplates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    fun fetchQuestions() = questionRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchCandidates() = candidateRepo.getAllCandidates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchInterview() = interviewRepo.getInterview(interviewId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun submitInterview(interview: Interview) = when(editing){
        true -> updateInterview(interview)
        false -> createInterview(interview)
    }

    fun createInterview(interview: Interview) = interviewRepo.createInterview(interview)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateInterview(interview: Interview) = interviewRepo.updateInterview(interview)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}

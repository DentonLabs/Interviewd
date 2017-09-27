package io.github.alexdenton.interviewd.conductinterview

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import io.github.alexdenton.interviewd.api.InterviewRepository
import io.github.alexdenton.interviewd.interview.Interview
import io.github.rfonzi.rxaware.BaseViewModel

/**
 * Created by ryan on 9/27/17.
 */
class ConductInterviewViewModel : BaseViewModel() {

    lateinit var interviewRepo: InterviewRepository

    lateinit var interview: Interview

    fun initWith(kodein: LazyKodein){
        interviewRepo = kodein.invoke().instance()
    }

    fun useInterview(interview: Interview) {
        this.interview = interview
    }
}
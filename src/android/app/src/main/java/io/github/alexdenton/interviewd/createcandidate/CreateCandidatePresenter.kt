package io.github.alexdenton.interviewd.createcandidate

import android.util.Log
import io.github.alexdenton.interviewd.api.CandidateRepository
import io.github.alexdenton.interviewd.interview.Candidate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/28/17.
 */
class CreateCandidatePresenter(val repo: CandidateRepository, val activity: CreateCandidateActivity) {
    fun submitCandidate() = repo.createCandidate(getCandidateFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({activity.onSuccessfulSubmit()},
                    {throwable -> couldNotConnect(throwable)})

    private fun couldNotConnect(throwable: Throwable) = Log.w("Network", throwable)


    fun getCandidateFromFields() = Candidate(0, activity.firstNameField.text.toString(), activity.lastNameField.text.toString())

}
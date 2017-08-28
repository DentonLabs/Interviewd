package io.github.alexdenton.interviewd.dashboard.candidates

import android.util.Log
import io.github.alexdenton.interviewd.api.CandidateRepository
import io.github.alexdenton.interviewd.interview.Candidate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 8/28/17.
 */
class CandidatesPresenter(val repo: CandidateRepository, val fragment: CandidatesFragment) {

    fun getAllCandidates(): Disposable = repo.getAllCandidates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> foundQuestions(list) },
                    { throwable -> couldNotConnect(throwable) })

    private fun foundQuestions(list: List<Candidate>) {
        updateCandidateList(list)
        fragment.onFoundQuestions()
    }

    private fun couldNotConnect(throwable: Throwable) {
        Log.w("Network", throwable)
        fragment.onCouldNotConnect()
    }

    private fun updateCandidateList(list: List<Candidate>) {
        fragment.candidates.clear()
        fragment.candidates.addAll(list)
        fragment.adapter.notifyDataSetChanged()
    }

}
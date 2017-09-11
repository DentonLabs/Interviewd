package io.github.alexdenton.interviewd.dashboard.interviews

import android.util.Log
import io.github.alexdenton.interviewd.api.InterviewRepository
import io.github.alexdenton.interviewd.interview.Interview
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/9/17.
 */
class InterviewsPresenter(val repo: InterviewRepository, val fragment: InterviewsFragment) {

    fun getAllInterviews(): Disposable = repo.getAllInterviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({list -> foundInterviews(list)},
                    {throwable -> couldNotConnect(throwable)})

    private fun foundInterviews(list: List<Interview>) {
        fragment.interviewList.clear()
        fragment.interviewList.addAll(list)
        fragment.adapter.notifyDataSetChanged()
    }

    private fun couldNotConnect(throwable: Throwable) {
        Log.w("Network", throwable)
        fragment.onCouldNotConnect()
    }

}
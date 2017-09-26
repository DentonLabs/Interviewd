package io.github.alexdenton.interviewd.dashboard.interviews


import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.createinterview.CreateInterviewActivity
import io.github.alexdenton.interviewd.interview.Interview
import io.github.rfonzi.rxaware.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class InterviewsFragment : BaseFragment() {

    lateinit var vm: InterviewsViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var addFab: FloatingActionButton
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    var interviewList: MutableList<Interview> = mutableListOf()
    val adapter = InterviewsAdapter(interviewList)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_interviews, container, false)

        vm = ViewModelProviders.of(this).get(InterviewsViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        recyclerView = view.findViewById(R.id.interviews_recyclerView)
        addFab = view.findViewById(R.id.interviews_addFab)
        progressBar = view.findViewById(R.id.interviews_progressBar)
        errorTextView = view.findViewById(R.id.interviews_errorTextView)
        val layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        vm.exposeAddFab(addFab.clicks())
        vm.getInterviewsObservable()
                .timeout(5, TimeUnit.SECONDS)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list -> foundInterviews(list) },
                        { couldNotConnect() })
                .lifecycleAware()

        return view
    }

    fun foundInterviews(list: List<Interview>) {
        interviewList.clear()
        interviewList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    fun couldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

}

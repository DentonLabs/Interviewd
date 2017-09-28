package io.github.alexdenton.interviewd.dashboard.candidates


import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.AndroidScope
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.createcandidate.CreateCandidateActivity
import io.github.alexdenton.interviewd.interview.Candidate
import io.github.rfonzi.rxaware.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CandidatesFragment : BaseFragment() {

    lateinit var vm: CandidatesViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addFab: FloatingActionButton

    lateinit var adapter: CandidatesAdapter

    var candidates: MutableList<Candidate> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_candidates, container, false)

        vm = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        recyclerView = view.findViewById(R.id.candidates_recyclerView)
        progressBar = view.findViewById(R.id.candidates_progressBar)
        errorTextView = view.findViewById(R.id.candidates_failedToGetCandidatesText)
        addFab = view.findViewById(R.id.candidates_addFab)

        adapter = CandidatesAdapter(candidates)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        vm.exposeAddFab(addFab.clicks())
        vm.exposeItemClicks(adapter.getItemClicks())
        vm.getCandidatesObservable()
                .timeout(5, TimeUnit.SECONDS)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({foundCandidates(it)},
                        {couldNotConnect()})
                .lifecycleAware()

        return view
    }

    fun couldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

    fun foundCandidates(candidates: List<Candidate>) {
        progressBar.visibility = View.GONE
        adapter.candidates = candidates
        adapter.notifyDataSetChanged()
    }

}

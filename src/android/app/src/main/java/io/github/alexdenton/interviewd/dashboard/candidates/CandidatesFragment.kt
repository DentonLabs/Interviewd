package io.github.alexdenton.interviewd.dashboard.candidates


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

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.CandidateRetrofitRepository
import io.github.alexdenton.interviewd.createcandidate.CreateCandidateActivity
import io.github.alexdenton.interviewd.interview.Candidate

class CandidatesFragment : Fragment() {

    lateinit var presenter: CandidatesPresenter

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addFab: FloatingActionButton

    lateinit var adapter: CandidatesAdapter

    var candidates: MutableList<Candidate> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_candidates, container, false)

        presenter = CandidatesPresenter(CandidateRetrofitRepository(context), this)

        recyclerView = view.findViewById(R.id.candidates_recyclerView)
        progressBar = view.findViewById(R.id.candidates_progressBar)
        errorTextView = view.findViewById(R.id.candidates_failedToGetCandidatesText)
        addFab = view.findViewById(R.id.candidates_addFab)

        adapter = CandidatesAdapter(candidates)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        addFab.setOnClickListener { onClickAddFab() }

        presenter.getAllCandidates()

        return view
    }

    fun onCouldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

    fun onFoundQuestions() {
        progressBar.visibility = View.GONE
    }

    fun onClickAddFab() {
        startActivity(Intent(context, CreateCandidateActivity::class.java))
    }

}

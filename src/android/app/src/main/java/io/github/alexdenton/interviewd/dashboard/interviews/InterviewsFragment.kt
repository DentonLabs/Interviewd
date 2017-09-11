package io.github.alexdenton.interviewd.dashboard.interviews


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
import io.github.alexdenton.interviewd.api.InterviewRetrofitRepository
import io.github.alexdenton.interviewd.interview.Interview


/**
 * A simple [Fragment] subclass.
 */
class InterviewsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var addFab: FloatingActionButton
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    var interviewList: MutableList<Interview> = mutableListOf()
    val adapter = InterviewsAdapter(interviewList)

    lateinit var presenter: InterviewsPresenter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_interviews, container, false)
        presenter = InterviewsPresenter(InterviewRetrofitRepository(context), this)

        recyclerView = view.findViewById(R.id.interviews_recyclerView)
        addFab = view.findViewById(R.id.interviews_addFab)
        progressBar = view.findViewById(R.id.interviews_progressBar)
        errorTextView = view.findViewById(R.id.interviews_errorTextView)
        val layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        presenter.getAllInterviews()

        return view
    }

    fun onCouldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

}

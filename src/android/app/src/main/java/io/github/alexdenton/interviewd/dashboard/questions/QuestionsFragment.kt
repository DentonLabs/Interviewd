package io.github.alexdenton.interviewd.dashboard.questions

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
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
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuestionsFragment : RxAwareFragment() {

    lateinit var vm: QuestionsViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addFab: FloatingActionButton
    val numCols = 2
    val adapter = QuestionsAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_questions, container, false)

        vm = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        recyclerView = view.findViewById(R.id.questions_recyclerView)
        progressBar = view.findViewById(R.id.questions_progressBar)
        errorTextView = view.findViewById(R.id.questions_failedToGetQuestionsText)
        addFab = view.findViewById(R.id.questions_addFab)

        recyclerView.layoutManager = GridLayoutManager(context, numCols)
        recyclerView.adapter = adapter

        vm.exposeAddFab(addFab.clicks())
        vm.exposeItemClicks(adapter.getItemClicks())
        vm.getQuestionsObservable()
                .timeout(5, TimeUnit.SECONDS)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({foundQuestions(it)},
                        {couldNotConnect()})
                .lifecycleAware()

        return view
    }

    fun couldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

    fun foundQuestions(list: List<Question>) {
        adapter.questions.clear()
        adapter.questions.addAll(list)
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

}

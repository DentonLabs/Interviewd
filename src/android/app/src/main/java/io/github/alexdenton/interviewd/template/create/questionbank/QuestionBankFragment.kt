package io.github.alexdenton.interviewd.template.create.questionbank


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.template.create.CreateTemplateViewModel
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class QuestionBankFragment : RxAwareFragment() {

    lateinit var recyclerView: RecyclerView
    val numRows = 2

    lateinit var adapter: QuestionBankAdapter

    lateinit var vm: CreateTemplateViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_bank, container, false)

        vm = ViewModelProviders.of(activity).get(CreateTemplateViewModel::class.java)
        vm.fetchAllQuestions()

        adapter = QuestionBankAdapter(mutableListOf())

        recyclerView = view.findViewById(R.id.questionBank_recyclerView)

        recyclerView.layoutManager = GridLayoutManager(context, numRows)
        recyclerView.adapter = adapter

        vm.allQuestions.subscribe { adapter.setQuestionBank(it.toMutableList()) }.lifecycleAware()
        adapter.setCheckedQuestions(vm.chosenQuestions.value)

        vm.exposeChosenQuestions(adapter.getCheckedQuestions())

        return view
    }

}

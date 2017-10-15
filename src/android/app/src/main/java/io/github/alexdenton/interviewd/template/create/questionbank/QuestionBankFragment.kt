package io.github.alexdenton.interviewd.template.create.questionbank

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.template.create.events.ToQuestionBankEvent
import io.github.alexdenton.interviewd.template.create.events.ToTemplateFormEvent
import io.github.rfonzi.rxaware.RxAwareFragment
import io.github.rfonzi.rxaware.bus.RxBus

/**
 * A simple [Fragment] subclass.
 */
class QuestionBankFragment : RxAwareFragment() {

    val numRows = 2
    lateinit var recyclerView: RecyclerView
    val adapter = QuestionBankAdapter(mutableListOf())
    lateinit var vm: QuestionBankViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_bank, container, false)

        vm = ViewModelProviders.of(this).get(QuestionBankViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        recyclerView = view.findViewById(R.id.questionBank_recyclerView)

        recyclerView.layoutManager = GridLayoutManager(context, numRows)
        recyclerView.adapter = adapter

        vm.fetchAllQuestions()
                .subscribe ({ questions -> adapter.setQuestionBank(questions.toMutableList()) },
                        {throwable -> throwable.printStackTrace()})

        RxBus.toObservable(ToQuestionBankEvent::class.java)
                .subscribe { adapter.setCheckedQuestions(it.checkedQuestions) }
                .lifecycleAware()

        return view
    }

    override fun onDestroy() {
        RxBus.post(ToTemplateFormEvent(adapter.checkedQuestions))
        super.onDestroy()
    }

}

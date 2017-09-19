package io.github.alexdenton.interviewd.createtemplate.questionbank


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendToTemplateFormEvent
import io.github.alexdenton.interviewd.question.Question


/**
 * A simple [Fragment] subclass.
 */
class QuestionBankFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var presenter: QuestionBankPresenter
    val numRows = 2

    var questionBank: MutableList<Question> = mutableListOf()
    var alreadyChecked: List<Question> = listOf()

    val adapter = QuestionBankAdapter(questionBank)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_bank, container, false)

        presenter = QuestionBankPresenter(QuestionRetrofitRepository(context), this)

        recyclerView = view.findViewById(R.id.questionBank_recyclerView)

        recyclerView.layoutManager = GridLayoutManager(context, numRows)
        recyclerView.adapter = adapter

        presenter.getCheckedQuestions()
        presenter.getAllQuestions()
        adapter.setCheckedQuestions(alreadyChecked)

        return view
    }


    override fun onPause() {
        RxBus.post(SendToTemplateFormEvent(adapter.checkedQuestions))
        super.onPause()
    }


    fun setUpQuestions(list: List<Question>) {
        questionBank.clear()
        questionBank.addAll(list)
        adapter.notifyDataSetChanged()
    }

    fun onCouldNotConnect() {
        TODO("not implemented")
    }

}

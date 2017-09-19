package io.github.alexdenton.interviewd.createtemplate.templateform


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.*
import io.github.alexdenton.interviewd.question.Question


/**
 * A simple [Fragment] subclass.
 */
class TemplateFormFragment : Fragment() {

    lateinit var titleField: EditText
    lateinit var estText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var submitButton: Button
    lateinit var addQuestionButton: Button

    lateinit var presenter: TemplateFormPresenter
    lateinit var adapter: TemplateFormAdapter
    lateinit var touchHelper: TemplateFormTouchHelper

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_template_form, container, false)

        titleField = view.findViewById(R.id.createTemplate_titleField)
        estText = view.findViewById(R.id.createTemplate_estDurationText)
        recyclerView = view.findViewById(R.id.createTemplate_recyclerView)
        submitButton = view.findViewById(R.id.createTemplate_submitButton)
        addQuestionButton = view.findViewById(R.id.createTemplate_addQuestionButton)
        presenter = TemplateFormPresenter(this, TemplateRetrofitRepository(context))
        adapter = TemplateFormAdapter(presenter.questionsFromBank)
        touchHelper = TemplateFormTouchHelper(adapter)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerView)


        submitButton.setOnClickListener { presenter.submitTemplate() }
        addQuestionButton.setOnClickListener { presenter.startAddingQuestions() }
        
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        RxBus.toObservable(SendToTemplateFormEvent::class.java)
//                .subscribe {
//                    presenter.questionsFromBank.clear()
//                    presenter.questionsFromBank.addAll(it.list)
//                    adapter.notifyDataSetChanged()
//                }
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        presenter.updatePickedQuestions()
        super.onStart()
    }

    override fun onDestroy() {
        presenter.disposeAll()
        super.onDestroy()
    }


    fun onSubmitSuccess() {
        RxBus.post(NavigateUpEvent())
    }

    fun onUpdateSuccess() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    fun switchToQuestionBank(pickedQuestions: List<Question>) {
        RxBus.post(SendPickedQuestionsEvent(pickedQuestions))
        RxBus.post(SwitchToQuestionBankEvent())
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}

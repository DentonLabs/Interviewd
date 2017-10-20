package io.github.alexdenton.interviewd.template.templateform


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.template.events.ToQuestionBankEvent
import io.github.alexdenton.interviewd.template.events.ToTemplateFormEvent
import io.github.rfonzi.rxaware.RxAwareFragment
import io.github.rfonzi.rxaware.bus.RxBus


/**
 * A simple [Fragment] subclass.
 */
class TemplateFormFragment : RxAwareFragment() {

    lateinit var titleField: EditText
    lateinit var estText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var addQuestionButton: Button
    lateinit var submitButton: Button

    lateinit var adapter: TemplateFormAdapter
    lateinit var touchHelper: TemplateFormTouchHelper

    lateinit var vm: TemplateFormViewModel

    override fun onAttach(context: Context?) {
        adapter = TemplateFormAdapter(mutableListOf())
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_template_form, container, false)
        vm = ViewModelProviders.of(activity).get(TemplateFormViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        titleField = view.findViewById(R.id.templateForm_titleField)
        estText = view.findViewById(R.id.templateForm_estDurationText)
        recyclerView = view.findViewById(R.id.templateForm_recyclerView)
        addQuestionButton = view.findViewById(R.id.templateForm_addQuestionButton)
        submitButton = view.findViewById(R.id.templateForm_submitButton)

        arguments?.getInt("editing_template")?.apply {
            vm.fetchTemplate(this)
                    .subscribe { template -> setupTemplate(template) }
                    .lifecycleAware()
            arguments = null
        }

        touchHelper = TemplateFormTouchHelper(adapter)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerView)

        submitButton.clicks()
                .map { Template(titleField.text.toString(), adapter.bankedQuestions, vm.templateId) }
                .flatMap { vm.submitTemplate(it).toObservable() }
                .subscribe { submitSuccess() }
                .lifecycleAware()

        addQuestionButton.clicks()
                .subscribe {
                    RxBus.post(ToQuestionBankEvent(adapter.bankedQuestions))
                    postToCurrentActivity(ShowQuestionBankFragment)
                }
                .lifecycleAware()

        vm.getChosenQuestions()
                .subscribe { adapter.setBankedQuestions(it) }
                .lifecycleAware()

        retainInstance = true
        setHasOptionsMenu(true)

        RxBus.toObservable(ToTemplateFormEvent::class.java)
                .subscribe { vm.useChosenQuestions(it.checkedQuestions) }

        return view
    }

    private fun submitSuccess() {
        toast("Template submitted!")
        postToCurrentActivity(Leave)
    }

    private fun setupTemplate(template: Template) {
        titleField.setText(template.name)
        estText.text = resources.getString(R.string.est, template.questions.sumBy { it.timeEstimate })
        vm.useChosenQuestions(template.questions)
        vm.templateId = template.id
        vm.editing = true
    }

}

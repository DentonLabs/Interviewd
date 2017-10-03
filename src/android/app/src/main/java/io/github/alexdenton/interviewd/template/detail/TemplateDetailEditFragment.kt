package io.github.alexdenton.interviewd.template.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.jakewharton.rxbinding2.support.v7.widget.dataChanges
import com.jakewharton.rxbinding2.widget.textChanges

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.template.create.questionbank.QuestionBankAdapter
import io.github.alexdenton.interviewd.template.create.templateform.TemplateFormAdapter
import io.github.alexdenton.interviewd.template.create.templateform.TemplateFormTouchHelper
import io.github.alexdenton.interviewd.entities.Template
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class TemplateDetailEditFragment : RxAwareFragment() {

    lateinit var vm: TemplateDetailViewModel

    lateinit var nameEdit: EditText
    lateinit var estimate: TextView
    lateinit var recyclerViewEdit: RecyclerView
    lateinit var addQuestionButton: Button
    lateinit var adapter: TemplateFormAdapter
    lateinit var addQuestionAdapter: QuestionBankAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_template_detail_edit, container, false)
        vm = ViewModelProviders.of(activity).get(TemplateDetailViewModel::class.java)
        (activity as TemplateDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        nameEdit = view.findViewById(R.id.templateDetail_nameEdit)
        estimate = view.findViewById(R.id.templateDetail_estimate)
        recyclerViewEdit = view.findViewById(R.id.templateDetail_recyclerViewEdit)
        addQuestionButton = view.findViewById(R.id.templateDetail_addQuestionButton)
        addQuestionAdapter = QuestionBankAdapter(mutableListOf())

        if(savedInstanceState == null){
            vm.getTemplateObservable()
                    .subscribe { setupTemplate(it) }
                    .lifecycleAware()
        }

        val addQuestionDialog = MaterialDialog.Builder(context)
                .title("Add Questions")
                .adapter(addQuestionAdapter, GridLayoutManager(context, 2))
                .positiveText("Done")
                .onPositive { dialog, which ->
                    adapter.setBankedQuestions(addQuestionAdapter.checkedQuestions)
                }
                .build()

        vm.exposeNameChanges(nameEdit.textChanges())
        vm.exposeQuestionChanges(adapter.dataChanges())
        addQuestionButton.setOnClickListener {
            vm.fetchAllQuestions()
            addQuestionDialog.show()
        }

        vm.getAllQuestionsObservable()
                .subscribe { addQuestionAdapter.setQuestionBank(it) }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_submit_cancel, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> {
                adapter.notifyDataSetChanged()
                vm.submitEdit()
            }
            R.id.menu_cancel -> vm.stopEditingTemplate()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupTemplate(template: Template) {
        nameEdit.setText(template.name)
        estimate.text = resources.getString(R.string.est, template.questions.sumBy { it.timeEstimate })
        adapter = TemplateFormAdapter(template.questions.toMutableList())
        addQuestionAdapter.setCheckedQuestions(template.questions)
        val touchHelper = TemplateFormTouchHelper(adapter)
        recyclerViewEdit.adapter = adapter
        recyclerViewEdit.layoutManager = LinearLayoutManager(context)
        recyclerViewEdit.addItemDecoration(touchHelper)
        touchHelper.attachToRecyclerView(recyclerViewEdit)
    }


}// Required empty public constructor

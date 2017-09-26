package io.github.alexdenton.interviewd.createinterview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormAdapter
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormTouchHelper
import io.github.alexdenton.interviewd.createtemplate.questionbank.QuestionBankAdapter
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.BaseActivity

class CreateInterviewActivity : BaseActivity() {

    lateinit var vm: CreateInterviewViewModel

    lateinit var candidateSpinner: Spinner
    lateinit var recyclerView: RecyclerView
    lateinit var addQuestionButton: Button
    lateinit var loadTemplateButton: Button

    val adapter = TemplateFormAdapter(mutableListOf())
    val loadTemplateAdapter = LoadTemplateAdapter(mutableListOf())
    val addQuestionAdapter = QuestionBankAdapter(mutableListOf())
    lateinit var loadTemplateDialog: MaterialDialog
    lateinit var addQuestionDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_interview)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(CreateInterviewViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        candidateSpinner = findViewById(R.id.createInterview_candidateSpinner)
        recyclerView = findViewById(R.id.createInterview_recyclerView)
        addQuestionButton = findViewById(R.id.createInterview_addQuestionButton)
        loadTemplateButton = findViewById(R.id.createInterview_loadTemplateButton)

        val touchHelper = TemplateFormTouchHelper(adapter)
        touchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(touchHelper)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val candidateSpinnerAdapter = CandidateArrayAdapter(this, mutableListOf())
        candidateSpinner.adapter = candidateSpinnerAdapter

        loadTemplateDialog = MaterialDialog.Builder(this)
                .title("Load Template")
                .adapter(loadTemplateAdapter, LinearLayoutManager(this))
                .build()

        addQuestionDialog = MaterialDialog.Builder(this)
                .title("Add Questions")
                .adapter(addQuestionAdapter, GridLayoutManager(this, 2))
                .positiveText("Done")
                .onPositive { dialog, which ->
                    vm.acceptQuestions(addQuestionAdapter.checkedQuestions)
                }
                .build()

        vm.exposeLoadTemplateButton(loadTemplateButton.clicks())
        vm.exposeLoadTemplateSelections(loadTemplateAdapter.getItemsClicked())
        vm.exposeAddQuestionButton(addQuestionButton.clicks())

        vm.getTemplateDialogSignal()
                .subscribe {
                    when (it) {
                        DialogSignal.SHOW -> loadTemplateDialog.show()
                        DialogSignal.HIDE -> loadTemplateDialog.hide()
                    }
                }
        vm.getQuestionDialogSignal()
                .subscribe { addQuestionDialog.show() }

        vm.getTemplatesObservable()
                .subscribe { list -> setupTemplateDialog(list) }
        vm.getAllQuestionsObservable()
                .subscribe { list -> setupQuestionDialog(list) }
        vm.getSelectedQuestionsObservable()
                .subscribe { list -> adapter.setBankedQuestions(list)}
        vm.getCandidatesObservable()
                .subscribe { list -> candidateSpinnerAdapter.setCandidates(list) }

    }

    fun setupTemplateDialog(list: List<Template>) {
        loadTemplateAdapter.templates.clear()
        loadTemplateAdapter.templates.addAll(list)
        loadTemplateAdapter.notifyDataSetChanged()
    }

    fun setupQuestionDialog(list: List<Question>) {
        addQuestionAdapter.clear()
        addQuestionAdapter.setQuestionBank(list)
        addQuestionAdapter.setCheckedQuestions(adapter.bankedQuestions)
        addQuestionAdapter.notifyDataSetChanged()
    }

}

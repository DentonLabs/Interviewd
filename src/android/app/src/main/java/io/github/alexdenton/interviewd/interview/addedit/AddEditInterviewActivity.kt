package io.github.alexdenton.interviewd.interview.addedit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.afollestad.materialdialogs.MaterialDialog
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.entities.Template
import io.github.alexdenton.interviewd.template.create.questionbank.QuestionBankAdapter
import io.github.alexdenton.interviewd.template.templateform.TemplateFormAdapter
import io.github.alexdenton.interviewd.template.templateform.TemplateFormTouchHelper
import io.github.rfonzi.rxaware.RxAwareActivity

class AddEditInterviewActivity : RxAwareActivity() {

    lateinit var vm: AddEditInterviewViewModel

    lateinit var nameEditText: EditText
    lateinit var candidateSpinner: Spinner
    lateinit var recyclerView: RecyclerView
    lateinit var addQuestionButton: Button
    lateinit var loadTemplateButton: Button

    val chosenQuestionsAdapter = TemplateFormAdapter(mutableListOf())
    val loadTemplateAdapter = LoadTemplateAdapter(mutableListOf())
    val addQuestionAdapter = QuestionBankAdapter(mutableListOf())
    lateinit var candidateSpinnerAdapter: CandidateArrayAdapter
    lateinit var loadTemplateDialog: MaterialDialog
    lateinit var addQuestionDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_interview)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(AddEditInterviewViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))

        intent.extras?.apply {
            vm.editing = true
            vm.interviewId = getInt("interviewId")
            vm.candidateId = getInt("candidateId")

            vm.fetchInterview()
                    .subscribe { interview -> setupInterview(interview)  }
                    .lifecycleAware()
        }

        nameEditText = findViewById(R.id.addEditInterview_interviewNameEditText)
        candidateSpinner = findViewById(R.id.addEditInterview_candidateSpinner)
        recyclerView = findViewById(R.id.addEditInterview_recyclerView)
        addQuestionButton = findViewById(R.id.addEditInterview_addQuestionButton)
        loadTemplateButton = findViewById(R.id.addEditInterview_loadTemplateButton)

        val touchHelper = TemplateFormTouchHelper(chosenQuestionsAdapter)
        touchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = chosenQuestionsAdapter
        recyclerView.addItemDecoration(touchHelper)
        recyclerView.layoutManager = LinearLayoutManager(this)

        candidateSpinnerAdapter = CandidateArrayAdapter(this, mutableListOf())
        candidateSpinner.adapter = candidateSpinnerAdapter

        buildDialogs()

        loadTemplateButton.clicks()
                .flatMap { vm.fetchTemplates().toObservable() }
                .subscribe { showTemplateDialog(it) }
                .lifecycleAware()

        loadTemplateAdapter.getItemsClicked()
                .subscribe {
                    setupTemplate(it)
                    loadTemplateDialog.dismiss()
                }
                .lifecycleAware()

        addQuestionButton.clicks()
                .flatMap { vm.fetchQuestions().toObservable() }
                .subscribe { showAddQuestionDialog(it) }
                .lifecycleAware()

        vm.fetchCandidates()
                .subscribe { candidates ->
                    candidateSpinnerAdapter.setCandidates(candidates)
                    candidateSpinner.setSelection(candidates.indexOfFirst { it.id == vm.candidateId })
                }
                .lifecycleAware()

    }

    private fun buildDialogs() {
        loadTemplateDialog = MaterialDialog.Builder(this)
                .title("Load Template")
                .adapter(loadTemplateAdapter, LinearLayoutManager(this))
                .build()

        addQuestionDialog = MaterialDialog.Builder(this)
                .title("Add Questions")
                .adapter(addQuestionAdapter, GridLayoutManager(this, 2))
                .positiveText("Done")
                .onPositive { dialog, which ->
                    setupQuestions(addQuestionAdapter.checkedQuestions)
                }
                .build()
    }

    fun showTemplateDialog(list: List<Template>) {
        loadTemplateAdapter.templates.clear()
        loadTemplateAdapter.templates.addAll(list)
        loadTemplateAdapter.notifyDataSetChanged()

        loadTemplateDialog.show()
    }

    fun showAddQuestionDialog(list: List<Question>) {
        addQuestionAdapter.clear()
        addQuestionAdapter.setQuestionBank(list)
        addQuestionAdapter.setCheckedQuestions(chosenQuestionsAdapter.bankedQuestions)
        addQuestionAdapter.notifyDataSetChanged()

        addQuestionDialog.show()
    }

    fun setupTemplate(template: Template) {
        nameEditText.setText(template.name)
        setupQuestions(template.questions)
    }

    fun setupQuestions(questions: List<Question>) {
        chosenQuestionsAdapter.setBankedQuestions(questions)
    }

    fun setupInterview(interview: Interview) {
        nameEditText.setText(interview.name)
        chosenQuestionsAdapter.setBankedQuestions(interview.questions)
    }

    fun getInterview() = Interview(vm.interviewId, getSelectedCandidate(), nameEditText.text.toString(), chosenQuestionsAdapter.bankedQuestions)

    fun getSelectedCandidate() = candidateSpinnerAdapter.candidates[candidateSpinner.selectedItemPosition]

    fun onSubmitSuccess() {
        toast("Inteview submitted")
        onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_submit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_submit -> vm.submitInterview(getInterview())
                    .subscribe({ onSubmitSuccess() },
                            { throwable -> throwable.printStackTrace() })
                    .lifecycleAware()
        }

        return super.onOptionsItemSelected(item)
    }
}

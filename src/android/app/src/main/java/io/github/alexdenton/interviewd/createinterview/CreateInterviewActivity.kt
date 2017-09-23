package io.github.alexdenton.interviewd.createinterview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.CandidateRetrofitRepository
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository
import io.github.rfonzi.rxaware.bus.RxBus
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormAdapter
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormTouchHelper
import io.github.alexdenton.interviewd.createtemplate.questionbank.QuestionBankAdapter
import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.question.Question

class CreateInterviewActivity : AppCompatActivity() {

    lateinit var autoComplete: AutoCompleteTextView
    lateinit var recyclerView: RecyclerView
    lateinit var editCandidateResultButton: ImageButton
    lateinit var addQuestionButton: Button
    lateinit var loadTemplateButton: Button



    val loadTemplateAdapter = LoadTemplateAdapter(mutableListOf())
    val addQuestionAdapter = QuestionBankAdapter(mutableListOf())
    lateinit var loadTemplateDialog: MaterialDialog
    lateinit var addQuestionDialog: MaterialDialog
    lateinit var spinnerAdapter: ArrayAdapter<Template>

    lateinit var presenter: CreateInterviewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_interview)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = CreateInterviewPresenter(TemplateRetrofitRepository(this),
                QuestionRetrofitRepository(this),
                CandidateRetrofitRepository(this),
                this)

        autoComplete = findViewById(R.id.createInterview_candidateAutocomplete)
        recyclerView = findViewById(R.id.createInterview_recyclerView)
        editCandidateResultButton = findViewById(R.id.createInterview_candidateResultEditButton)
        addQuestionButton = findViewById(R.id.createInterview_addQuestionButton)
        loadTemplateButton = findViewById(R.id.createInterview_loadTemplateButton)

        val adapter = TemplateFormAdapter(presenter.questions)
        val touchHelper = TemplateFormTouchHelper(adapter)
        touchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(touchHelper)
        recyclerView.layoutManager = LinearLayoutManager(this)


        loadTemplateDialog = MaterialDialog.Builder(this)
                .title("Load Template")
                .adapter(loadTemplateAdapter, LinearLayoutManager(this))
                .build()

        addQuestionDialog = MaterialDialog.Builder(this)
                .title("Add Questions")
                .adapter(addQuestionAdapter, GridLayoutManager(this, 2))
                .positiveText("Done")
                .onPositive { dialog, which ->
                    adapter.bankedQuestions.clear()
                    adapter.bankedQuestions.addAll(addQuestionAdapter.checkedQuestions)
                    adapter.notifyDataSetChanged()
                }
                .build()

        loadTemplateButton.setOnClickListener {
            loadTemplateDialog.show()

            presenter.getTemplates()

//            RxBus.toObservable(TemplateSelectedEvent::class.java).subscribe { event ->
//                adapter.bankedQuestions.clear()
//                adapter.bankedQuestions.addAll(event.template.questions)
//                adapter.notifyDataSetChanged()
//                loadTemplateDialog.dismiss()
//                RxBus.clear()
//            }
        }

        addQuestionButton.setOnClickListener {
            addQuestionDialog.show()
            presenter.getQuestions()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setupTemplateDialog(list: List<Template>) {
        loadTemplateAdapter.templates.clear()
        loadTemplateAdapter.templates.addAll(list)
        loadTemplateAdapter.notifyDataSetChanged()
    }

    fun setupQuestionDialog(list: List<Question>) {
        addQuestionAdapter.clear()
        addQuestionAdapter.setQuestionBank(list)
        addQuestionAdapter.setCheckedQuestions(presenter.questions)
        addQuestionAdapter.notifyDataSetChanged()
    }

}

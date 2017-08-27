package io.github.alexdenton.interviewd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository
import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendQuestionListEvent
import io.github.alexdenton.interviewd.bus.events.SendToQuestionBankEvent

class CreateTemplateActivity : AppCompatActivity() {

    lateinit var titleField: EditText
    lateinit var estText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var submitButton: Button
    lateinit var addQuestionButton: Button

    lateinit var presenter: CreateTemplatePresenter
    lateinit var adapter: CreateTemplateAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_template)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleField = findViewById(R.id.createTemplate_titleField)
        estText = findViewById(R.id.createTemplate_estDurationText)
        recyclerView = findViewById(R.id.createTemplate_recyclerView)
        submitButton = findViewById(R.id.createTemplate_submitButton)
        addQuestionButton = findViewById(R.id.createTemplate_addQuestionButton)
        presenter = CreateTemplatePresenter(this, TemplateRetrofitRepository(this))
        adapter = CreateTemplateAdapter(presenter.questionsFromBank)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        submitButton.setOnClickListener { presenter.submitTemplate() }
        addQuestionButton.setOnClickListener { presenter.startAddingQuestions() }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        RxBus.post(SendQuestionListEvent(presenter.questionsFromBank))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        RxBus.toObservable(SendQuestionListEvent::class.java)
                .subscribe {
                    presenter.questionsFromBank.clear()
                    presenter.questionsFromBank.addAll(it.list)
                    adapter.notifyDataSetChanged()
                }
                .dispose()

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        presenter.updatePickedQuestions()
        super.onStart()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        RxBus.clear()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        RxBus.clear()
        super.onBackPressed()
    }

    fun onSubmitSuccess() {
        finish()
    }

    fun switchToQuestionBank(pickedQuestions: List<Question>) {
        val intent = Intent(this, QuestionBankActivity::class.java)
        RxBus.post(SendToQuestionBankEvent(pickedQuestions))
        startActivity(intent)
    }

    fun onUpdateSuccess() {
        recyclerView.adapter.notifyDataSetChanged()
    }
}

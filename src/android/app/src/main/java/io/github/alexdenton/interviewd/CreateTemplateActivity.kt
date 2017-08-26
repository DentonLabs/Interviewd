package io.github.alexdenton.interviewd

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
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
import io.github.alexdenton.interviewd.bus.events.FlushEvent
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

        actionBar?.setDisplayHomeAsUpEnabled(true)

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
        outState?.putString("interview_title", titleField.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        titleField.setText(savedInstanceState?.getString("interview_title"))
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        presenter.updatePickedQuestions()
        super.onResume()
    }

    override fun onDestroy() {
        RxBus.post(FlushEvent::class.java)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

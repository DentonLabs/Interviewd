package io.github.alexdenton.interviewd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.github.alexdenton.interviewd.api.TemplateRetrofitRepository

class CreateTemplateActivity : AppCompatActivity() {

    lateinit var titleField: EditText
    lateinit var estText: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var submitButton: Button
    lateinit var addQuestionButton: Button

    lateinit var presenter: CreateTemplatePresenter


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

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CreateTemplateAdapter(presenter.questionsFromBank)

        submitButton.setOnClickListener { presenter.submitTemplate() }
        addQuestionButton.setOnClickListener { Toast.makeText(this, "Not supported yet", Toast.LENGTH_SHORT).show() }

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
}

package io.github.alexdenton.interviewd.detailtemplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Template
import io.github.rfonzi.rxaware.RxAwareActivity

class TemplateDetailActivity : RxAwareActivity() {

    lateinit var templateName: TextView
    lateinit var templateEst: TextView
    lateinit var questionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val template = receive() as Template

        templateName = findViewById(R.id.templateDetail_name)
        templateEst = findViewById(R.id.templateDetail_estimate)
        questionRecyclerView = findViewById(R.id.templateDetail_recyclerView)

        val adapter = QuestionDetailAdapter(template.questions)
        questionRecyclerView.adapter = adapter
        questionRecyclerView.layoutManager = LinearLayoutManager(this)

        templateName.text = template.name
        templateEst.text = resources.getString(R.string.est, template.questions.sumBy { it.timeEstimate })

    }
}

package io.github.alexdenton.interviewd.dashboard.questions

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository
import io.github.alexdenton.interviewd.createquestion.CreateQuestionActivity
import io.github.alexdenton.interviewd.question.Question

class QuestionsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var addFab: FloatingActionButton
    val numCols = 2
    var questions: List<Question> = emptyList()
    lateinit var presenter: QuestionsPresenter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_questions, container, false)

        presenter = QuestionsPresenter(QuestionRetrofitRepository(view.context), this)

        recyclerView = view.findViewById(R.id.questions_recyclerView)
        progressBar = view.findViewById(R.id.questions_progressBar)
        errorTextView = view.findViewById(R.id.questions_failedToGetQuestionsText)
        addFab = view.findViewById(R.id.questions_addFab)

        recyclerView.layoutManager = GridLayoutManager(context, numCols)
        recyclerView.adapter = QuestionsAdapter(questions)

        addFab.setOnClickListener { onClickAddFab() }


        presenter.getAllQuestions()

        return view
    }

    fun onCouldNotConnect() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
    }

    fun onFoundQuestions(list: List<Question>) {
        recyclerView.adapter = QuestionsAdapter(list)
        recyclerView.adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    fun onClickAddFab() {
        startActivity(Intent(context, CreateQuestionActivity::class.java))
    }

}

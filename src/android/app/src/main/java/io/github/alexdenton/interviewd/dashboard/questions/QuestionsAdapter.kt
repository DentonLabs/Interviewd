package io.github.alexdenton.interviewd.dashboard.questions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Observable

/**
 * Created by ryan on 8/11/17.
 */
class QuestionsAdapter(var questions: MutableList<Question>) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private val questionClicks: PublishRelay<Question> = PublishRelay.create()
    fun getItemClicks(): Observable<Question> = questionClicks

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.card_question, parent, false)

        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder?, position: Int) {
        holder?.nameView?.text = questions[position].name
        holder?.descView?.text = questions[position].description

        holder?.itemView?.setOnClickListener {
            questionClicks.accept(questions[position])
        }
    }

    override fun getItemCount(): Int = questions.size

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.card_questionName)
        val descView: TextView = itemView.findViewById(R.id.card_questionDescription)
    }
}


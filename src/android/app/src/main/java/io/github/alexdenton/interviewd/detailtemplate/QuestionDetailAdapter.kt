package io.github.alexdenton.interviewd.detailtemplate

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 9/29/17.
 */
class QuestionDetailAdapter(val questions: List<Question>) : RecyclerView.Adapter<QuestionDetailAdapter.QuestionDetailViewHolder>() {
    override fun getItemCount(): Int = questions.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionDetailViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.list_question_static, parent, false)

        return QuestionDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionDetailViewHolder?, position: Int) {
        holder?.name?.text = questions[position].name
        holder?.est?.text = holder?.itemView?.resources?.getString(R.string.est, questions[position].timeEstimate)
    }

    class QuestionDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.listQuestionStatic_name)
        val est: TextView = itemView.findViewById(R.id.listQuestionStatic_est)
    }
}
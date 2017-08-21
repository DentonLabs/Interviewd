package io.github.alexdenton.interviewd

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/18/17.
 */
class CreateTemplateAdapter(val bankedQuestions: List<Question>) : RecyclerView.Adapter<CreateTemplateAdapter.BankedQuestionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BankedQuestionViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.list_banked_question, parent, false)

        return BankedQuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankedQuestionViewHolder?, position: Int) {
        holder?.nameView?.text = bankedQuestions[position].name
    }

    override fun getItemCount(): Int = bankedQuestions.size

    class BankedQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.listBankedQuestion_questionName)
    }
}
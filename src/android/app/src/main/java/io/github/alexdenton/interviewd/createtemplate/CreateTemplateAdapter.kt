package io.github.alexdenton.interviewd.createtemplate

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/18/17.
 */
class CreateTemplateAdapter(val bankedQuestions: MutableList<Question>) : RecyclerView.Adapter<CreateTemplateAdapter.BankedQuestionViewHolder>(){

    var touchHelper: CreateTemplateTouchHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BankedQuestionViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.list_banked_question, parent, false)

        return BankedQuestionViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BankedQuestionViewHolder?, position: Int) {
        holder?.nameView?.text = bankedQuestions[position].name
        holder?.reorderButton?.setOnTouchListener { view, motionEvent ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN)
                touchHelper?.startDrag(holder)
            false
        }
    }

    override fun getItemCount(): Int = bankedQuestions.size

    class BankedQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.listBankedQuestion_questionName)
        val reorderButton: ImageButton = itemView.findViewById(R.id.listBankedQuestion_reorderButton)
    }
}
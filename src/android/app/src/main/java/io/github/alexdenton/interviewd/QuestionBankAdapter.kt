package io.github.alexdenton.interviewd

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 8/23/17.
 */
class QuestionBankAdapter(val questionBank: List<Question>) : RecyclerView.Adapter<QuestionBankAdapter.QuestionBankViewHolder>() {

    val checkedQuestions: MutableList<Question> = mutableListOf()

    //private val onClickSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionBankViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.card_question_in_bank, parent, false)

        return QuestionBankViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionBankViewHolder?, position: Int) {
        holder?.nameView?.text = questionBank[position].name
        holder?.descView?.text = questionBank[position].description
        holder?.checked?.isChecked = questionBank[position] in checkedQuestions

        holder?.itemView?.setOnClickListener {
            //onClickSubject.onNext(position)

            if (holder.checked.isChecked)
                checkedQuestions.remove(questionBank[position])
            else
                checkedQuestions.add(questionBank[position])

            holder?.checked?.toggle()
        }
    }

    override fun getItemCount(): Int = questionBank.size

    fun setCheckedQuestions(list: List<Question>) {
        checkedQuestions.clear()
        checkedQuestions.addAll(list)
        notifyDataSetChanged()
    }

    //fun getPositionClicked(): Observable<Int> = onClickSubject

    class QuestionBankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.card_bankQuestionName)
        val descView: TextView = itemView.findViewById(R.id.card_bankQuestionDescription)
        val checked: CheckBox = itemView.findViewById(R.id.card_bankQuestionCheckBox)
    }
}

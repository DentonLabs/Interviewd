package io.github.alexdenton.interviewd.createtemplate.questionbank

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxAdapter
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by ryan on 8/23/17.
 */
class QuestionBankAdapter(val questionBank: MutableList<Question>) : RecyclerView.Adapter<QuestionBankAdapter.QuestionBankViewHolder>() {

    private val itemClicks: PublishRelay<Int> = PublishRelay.create()
    val checkedQuestions: MutableList<Question> = mutableListOf()

    private val disposables = CompositeDisposable()

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

            itemClicks.accept(holder?.adapterPosition)

        }

        disposables.add(holder?.itemView?.clicks()?.subscribe { itemClicks.accept(holder?.adapterPosition) }!!)
    }

    fun setQuestionBank(list: List<Question>) {
        questionBank.clear()
        questionBank.addAll(list)
        notifyDataSetChanged()
    }

    fun setCheckedQuestions(list: List<Question>) {
        checkedQuestions.clear()
        checkedQuestions.addAll(list)
        notifyDataSetChanged()
    }

    fun getItemClicks(): Observable<Int> = itemClicks

    override fun getItemCount(): Int = questionBank.size

    fun clear() {
        checkedQuestions.clear()
        questionBank.clear()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposables.clear()
    }

    class QuestionBankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.card_bankQuestionName)
        val descView: TextView = itemView.findViewById(R.id.card_bankQuestionDescription)
        val checked: CheckBox = itemView.findViewById(R.id.card_bankQuestionCheckBox)
    }
}

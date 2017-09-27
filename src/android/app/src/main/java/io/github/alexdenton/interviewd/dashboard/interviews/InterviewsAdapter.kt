package io.github.alexdenton.interviewd.dashboard.interviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Interview
import io.reactivex.Observable

/**
 * Created by ryan on 9/8/17.
 */
class InterviewsAdapter(val list: List<Interview>) : RecyclerView.Adapter<InterviewsAdapter.InterviewViewHolder>() {

    private val itemClicks: PublishRelay<Interview> = PublishRelay.create()
    fun getItemClicks(): Observable<Interview> = itemClicks

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InterviewViewHolder?, position: Int) {
        holder?.candidateView?.text = list[position].candidate.firstName + " " + list[position].candidate.lastName
        holder?.nameView?.text = list[position].name

        holder?.itemView?.setOnClickListener {
            itemClicks.accept(list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InterviewViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_interview, parent, false)

        return InterviewViewHolder(view)
    }

    class InterviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var candidateView: TextView = itemView.findViewById(R.id.card_interviewCandidate)
        var nameView: TextView = itemView.findViewById(R.id.card_interviewName)
    }
}
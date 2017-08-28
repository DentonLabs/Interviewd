package io.github.alexdenton.interviewd.dashboard.candidates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Candidate

/**
 * Created by ryan on 8/28/17.
 */
class CandidatesAdapter(var candidates: List<Candidate>) : RecyclerView.Adapter<CandidatesAdapter.CandidateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CandidateViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.card_candidate, parent, false)

        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder?, position: Int) {
        holder?.nameView?.text = "${candidates[position].firstName} ${candidates[position].lastName}"
    }

    override fun getItemCount(): Int = candidates.size

    class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView: TextView = itemView.findViewById(R.id.card_candidateName)
        val avatarView: ImageView = itemView.findViewById(R.id.card_candidateImage)
        val editIconView: ImageButton = itemView.findViewById(R.id.card_candidateEditIcon)
    }
}
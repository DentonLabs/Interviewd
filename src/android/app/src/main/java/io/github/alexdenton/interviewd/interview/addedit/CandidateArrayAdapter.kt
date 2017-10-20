package io.github.alexdenton.interviewd.interview.addedit

import android.content.Context
import android.widget.ArrayAdapter
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate

/**
 * Created by ryan on 9/26/17.
 */
class CandidateArrayAdapter(context: Context, val candidates: MutableList<Candidate>)
    : ArrayAdapter<Candidate>(context, R.layout.support_simple_spinner_dropdown_item, candidates) {

    fun setCandidates(list: List<Candidate>){
        candidates.clear()
        candidates.addAll(list)
        notifyDataSetChanged()
    }
}
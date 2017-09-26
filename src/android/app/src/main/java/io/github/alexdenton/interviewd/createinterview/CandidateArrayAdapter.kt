package io.github.alexdenton.interviewd.createinterview

import android.content.Context
import android.widget.ArrayAdapter
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Candidate
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

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
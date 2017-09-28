package io.github.alexdenton.interviewd.conductinterview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 9/27/17.
 */
class QuestionPagerAdapter(fm: FragmentManager, val questions: List<Question>) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        val fragment = QuestionPageFragment()
        val bundle = Bundle()
        bundle.putString("name", questions[position].name)
        bundle.putString("desc", questions[position].description)
        bundle.putInt("est", questions[position].timeEstimate)
        bundle.putInt("currentPage", position + 1)
        bundle.putInt("totalPages", questions.size)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int = questions.size
}
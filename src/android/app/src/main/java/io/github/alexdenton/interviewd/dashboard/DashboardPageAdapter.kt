package io.github.alexdenton.interviewd.dashboard

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.github.alexdenton.interviewd.dashboard.candidates.CandidatesFragment
import io.github.alexdenton.interviewd.dashboard.interviews.InterviewsFragment
import io.github.alexdenton.interviewd.dashboard.questions.QuestionsFragment
import io.github.alexdenton.interviewd.dashboard.templates.TemplatesFragment

/**
 * Created by ryan on 10/21/17.
 */
class DashboardPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val pages: List<Fragment> = listOf(InterviewsFragment(), QuestionsFragment(), TemplatesFragment(), CandidatesFragment())

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getCount(): Int = pages.size
}
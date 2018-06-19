package io.github.alexdenton.interviewd.dashboard

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import io.github.alexdenton.interviewd.dashboard.questions.QuestionsFragment
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.dashboard.candidates.CandidatesFragment
import io.github.alexdenton.interviewd.dashboard.interviews.InterviewsFragment
import io.github.alexdenton.interviewd.dashboard.templates.TemplatesFragment
import io.github.rfonzi.rxaware.RxAwareActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : RxAwareActivity() {

    lateinit var viewPager: DashboardViewPager
    lateinit var navigation: BottomNavigationView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_questions -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_templates -> {
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_candidates -> {
                viewPager.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewPager = findViewById(R.id.dashboard_viewPager)
        navigation = findViewById(R.id.navigation)
        viewPager.adapter = DashboardPageAdapter(supportFragmentManager)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        
        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_dashboard
        }

    }
}

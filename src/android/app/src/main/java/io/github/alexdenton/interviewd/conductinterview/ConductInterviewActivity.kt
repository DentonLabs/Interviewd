package io.github.alexdenton.interviewd.conductinterview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Interview
import io.github.rfonzi.rxaware.BaseActivity

class ConductInterviewActivity : BaseActivity() {

    lateinit var vm: ConductInterviewViewModel

    lateinit var candidateNameText: TextView
    lateinit var interviewTitleText: TextView
    lateinit var questionViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conduct_interview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(ConductInterviewViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))
        vm.useInterview(receive() as Interview)

        candidateNameText = findViewById(R.id.conductInterview_candidateName)
        interviewTitleText = findViewById(R.id.conductInterview_interviewPosition)
        questionViewPager = findViewById(R.id.conductInterview_questionViewPager)

        val viewPagerAdapter = QuestionPagerAdapter(supportFragmentManager, vm.interview.questions)
        questionViewPager.adapter = viewPagerAdapter

        setupStaticInfo(vm.interview)

    }

    fun setupStaticInfo(interview: Interview){
        candidateNameText.text = interview.candidate.toString()
        interviewTitleText.text = interview.name
    }
}

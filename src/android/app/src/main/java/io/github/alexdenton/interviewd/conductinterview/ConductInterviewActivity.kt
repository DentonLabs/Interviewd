package io.github.alexdenton.interviewd.conductinterview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.support.v4.view.pageSelections
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.interview.Interview
import io.github.rfonzi.rxaware.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConductInterviewActivity : BaseActivity() {

    lateinit var vm: ConductInterviewViewModel

    lateinit var candidateNameText: TextView
    lateinit var interviewTitleText: TextView
    lateinit var questionViewPager: QuestionViewPager
    lateinit var nextQuestionName: TextView
    lateinit var nextQuestionButton: ImageButton

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
        nextQuestionName = findViewById(R.id.conductInterview_nextQuestionName)
        nextQuestionButton = findViewById(R.id.conductInterview_nextButton)

        val viewPagerAdapter = QuestionPagerAdapter(supportFragmentManager, vm.interview.questions)
        questionViewPager.adapter = viewPagerAdapter

        vm.getNextQuestionStringObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateNextQuestion(it) }
                .lifecycleAware()

        vm.getNextPageSignal()
                .subscribe { questionViewPager.currentItem = it }

        vm.exposeNextClicks(nextQuestionButton.clicks())
        vm.exposeCurrentPage(Observable.concat(Observable.just(vm.currentPage), questionViewPager.pageSelections().skipInitialValue()))

        setupInfo(vm.interview)

    }

    private fun updateNextQuestion(nextQuestionString: String) {
        nextQuestionName.text = nextQuestionString
    }

    fun setupInfo(interview: Interview){
        candidateNameText.text = interview.candidate.toString()
        interviewTitleText.text = interview.name
    }
}

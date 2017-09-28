package io.github.alexdenton.interviewd.conductinterview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
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
    lateinit var interviewEstimateText: TextView
    lateinit var questionViewPager: QuestionViewPager
    lateinit var nextQuestionName: TextView
    lateinit var nextQuestionButton: ImageButton
    lateinit var timer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conduct_interview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(ConductInterviewViewModel::class.java)
        vm.initWith(LazyKodein(appKodein))
        vm.useInterview(receive() as Interview)

        candidateNameText = findViewById(R.id.conductInterview_candidateName)
        interviewTitleText = findViewById(R.id.conductInterview_interviewPosition)
        interviewEstimateText = findViewById(R.id.conductInterview_timeEstimate)
        questionViewPager = findViewById(R.id.conductInterview_questionViewPager)
        nextQuestionName = findViewById(R.id.conductInterview_nextQuestionName)
        nextQuestionButton = findViewById(R.id.conductInterview_nextButton)
        timer = findViewById(R.id.conductInterview_timer)

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

        setupStaticInfo(vm.interview)
        setupTimer()

    }

    private fun updateNextQuestion(nextQuestionString: String) {
        nextQuestionName.text = nextQuestionString
    }

    fun setupStaticInfo(interview: Interview){
        candidateNameText.text = interview.candidate.toString()
        interviewTitleText.text = interview.name
        interviewEstimateText.text = resources.getString(R.string.est, interview.questions.sumBy { it.timeEstimate })
    }

    override fun onPause() {
        super.onPause()
        timer.stop()
    }

    override fun onResume() {
        super.onResume()
        timer.start()
    }

    fun setupTimer(){

        timer.setOnChronometerTickListener {
            val time = SystemClock.elapsedRealtime() - it.base
            val mins = time / 60000

            it.text = resources.getString(R.string.elapsed, mins)
        }
    }
}

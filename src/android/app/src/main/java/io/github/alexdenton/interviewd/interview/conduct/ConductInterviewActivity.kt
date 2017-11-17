package io.github.alexdenton.interviewd.interview.conduct

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.constraint.Group
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.support.v4.view.pageSelections
import com.jakewharton.rxbinding2.view.clicks
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Interview
import io.github.alexdenton.interviewd.interview.addedit.AddEditInterviewActivity
import io.github.rfonzi.rxaware.RxAwareActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConductInterviewActivity : RxAwareActivity() {

    lateinit var vm: ConductInterviewViewModel

    lateinit var candidateNameText: TextView
    lateinit var interviewTitleText: TextView
    lateinit var interviewEstimateText: TextView
    lateinit var questionViewPager: QuestionViewPager
    lateinit var nextQuestionName: TextView
    lateinit var nextQuestionButton: ImageButton
    lateinit var timer: Chronometer
    lateinit var startButton: ImageButton
    lateinit var playGroup: Group
    lateinit var viewPagerAdapter: QuestionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conduct_interview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(ConductInterviewViewModel::class.java)

        candidateNameText = findViewById(R.id.conductInterview_candidateName)
        interviewTitleText = findViewById(R.id.conductInterview_interviewPosition)
        interviewEstimateText = findViewById(R.id.conductInterview_timeEstimate)
        questionViewPager = findViewById(R.id.conductInterview_questionViewPager)
        nextQuestionName = findViewById(R.id.conductInterview_nextQuestionName)
        nextQuestionButton = findViewById(R.id.conductInterview_nextButton)
        timer = findViewById(R.id.conductInterview_timer)
        playGroup = findViewById(R.id.conductInterview_playGroup)
        startButton = findViewById(R.id.conductInterview_startButton)

        if(savedInstanceState == null){
            vm.initWith(LazyKodein(appKodein))
            vm.useId(receive() as Long)
        }

        viewPagerAdapter = QuestionPagerAdapter(supportFragmentManager, mutableListOf())

        vm.getNextQuestionStringObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateNextQuestion(it) }
                .lifecycleAware()

        vm.getNextPageSignal()
                .subscribe { questionViewPager.currentItem = it }
                .lifecycleAware()

        vm.getStartSignal()
                .subscribe { startInterview() }
                .lifecycleAware()

        startButton.clicks()
                .subscribe {
                    vm.inProgress = true
                    startInterview()
                }

        nextQuestionButton.clicks()
                .subscribe {
                    if(vm.currentPage == viewPagerAdapter.questions.size - 1) {
                        toast("Finished the interview")
                        navigateUp()
                    }
                    else {
                        questionViewPager.currentItem = vm.currentPage + 1
                    }
                }

        questionViewPager.pageSelections().skipInitialValue()
                .subscribe { when(it){
                    viewPagerAdapter.questions.size - 1 -> updateNextQuestion("Finish interview")
                    else -> updateNextQuestion(viewPagerAdapter.questions[it + 1].name)
                }
                    vm.currentPage = it }
                .lifecycleAware()


    }

    override fun onPause() {
        super.onPause()
        if (playGroup.visibility == View.VISIBLE) timer.stop()
    }

    override fun onResume() {
        super.onResume()

        vm.fetchInterview(vm.interviewId)
                .subscribe { interview -> setupInterview(interview) }
                .lifecycleAware()

        if (playGroup.visibility == View.VISIBLE) timer.start()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putLong("time", timer.base)
        if(vm.inProgress) pauseInterview()
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        timer.base = savedInstanceState?.getLong("time") ?: SystemClock.elapsedRealtime()
        if(vm.inProgress) resumeInterview()
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun updateNextQuestion(nextQuestionString: String) {
        nextQuestionName.text = nextQuestionString
    }

    private fun setupInterview(interview: Interview) {
        setupStaticInfo(interview)
        vm.candidateId = interview.candidate.id
        viewPagerAdapter.questions.clear()
        viewPagerAdapter.questions.addAll(interview.questions)
        viewPagerAdapter.notifyDataSetChanged()
        questionViewPager.adapter = viewPagerAdapter
    }

    fun setupStaticInfo(interview: Interview){
        candidateNameText.text = interview.candidate.toString()
        interviewTitleText.text = interview.name
        interviewEstimateText.text = resources.getString(R.string.est, interview.questions.sumBy { it.timeEstimate })
    }

    fun setupTimer(){
        timer.base = SystemClock.elapsedRealtime()
        setupTickListener()
        timer.start()
    }

    fun setupTickListener() =  timer.setOnChronometerTickListener {
        val time = SystemClock.elapsedRealtime() - it.base
        val mins = time / 60000

        it.text = resources.getString(R.string.elapsed, mins)
    }

    fun startInterview(){
        startButton.visibility = View.GONE
        playGroup.visibility = View.VISIBLE
        questionViewPager.currentItem = 0
        questionViewPager.swipeEnabled = false
        setupTimer()
        invalidateOptionsMenu()
    }

    fun pauseInterview(){
        timer.stop()
    }

    fun resumeInterview(){
        startButton.visibility = View.GONE
        playGroup.visibility = View.VISIBLE
        questionViewPager.swipeEnabled = false
        setupTickListener()
        timer.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!vm.inProgress) menuInflater?.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_edit -> {
                startActivity(Intent(baseContext, AddEditInterviewActivity::class.java)
                        .apply {
                            putExtra("interviewId", vm.interviewId)
                            putExtra("candidateId", vm.candidateId)
                        })
            }
            R.id.menu_delete -> showDeleteConfirmation()

        }

        return super.onOptionsItemSelected(item)
    }

    fun showDeleteConfirmation() = MaterialDialog.Builder(this)
            .content("Are you sure you want to delete ${candidateNameText.text}'s ${interviewTitleText.text} interview?")
            .positiveText("Okay")
            .negativeText("Cancel")
            .onPositive { dialog, which ->
                vm.deleteInterview()
                        .subscribe { success -> onDeleteInterview(success) }
                        .lifecycleAware()
            }
            .onNegative { dialog, which -> dialog.dismiss() }
            .build()
            .show()

    fun onDeleteInterview(interview: Interview){
        toast("Deleted ${interview.candidate.firstName}'s ${interview.name} interview")
        navigateUp()
    }
}

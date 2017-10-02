package io.github.alexdenton.interviewd.detailquestion

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.RxAwareActivity
import io.github.rfonzi.rxaware.RxAwareFragment

class QuestionDetailActivity : RxAwareActivity() {

    lateinit var vm: QuestionDetailViewModel
    var currentFragment: RxAwareFragment = QuestionDetailShowFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        vm = ViewModelProviders.of(this).get(QuestionDetailViewModel::class.java)
        vm.init(LazyKodein(appKodein), receive() as Int)

        if (savedInstanceState == null){
            fragmentTransaction {
                add(R.id.questionDetail_fragmentContainer, currentFragment)
            }
        }


        vm.getFragmentSignal()
                .subscribe { switchFragments(it) }
                .lifecycleAware()

    }

    private fun switchFragments(signal: QuestionDetailSignal) {
        currentFragment = when (signal) {
            QuestionDetailSignal.SHOW -> QuestionDetailShowFragment()
            QuestionDetailSignal.EDIT -> QuestionDetailEditFragment()
        }
        fragmentTransaction {
            replace(R.id.questionDetail_fragmentContainer, currentFragment)
        }
    }

}


package io.github.alexdenton.interviewd.detailquestion

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.MenuItem
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareActivity

class QuestionDetailActivity : RxAwareActivity() {

    lateinit var vm: QuestionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        vm = ViewModelProviders.of(this).get(QuestionDetailViewModel::class.java)
        vm.init(LazyKodein(appKodein), receive() as Int)

        fragmentTransaction {
            add(R.id.questionDetail_fragmentContainer, QuestionDetailShowFragment())
        }

    }

}


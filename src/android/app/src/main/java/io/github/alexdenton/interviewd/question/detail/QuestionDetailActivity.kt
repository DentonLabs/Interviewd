package io.github.alexdenton.interviewd.question.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.QuestionFormFragment
import io.github.rfonzi.rxaware.RxAwareActivity

class QuestionDetailActivity : RxAwareActivity() {

    var questionId = 0
    var editing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        questionId = receive() as Int

        if (savedInstanceState == null){
            fragmentTransaction {
                add(R.id.questionDetail_fragmentContainer, QuestionDetailFragment().withId())
            }
        }

        onPost<QuestionDetailRouter>()
                .subscribe {
                    when (it) {
                        QuestionDetailRouter.SHOW -> switchToDetail()
                        QuestionDetailRouter.EDIT -> switchToEdit()
                    }
                }
                .lifecycleAware()
    }

    fun switchToDetail() {
        fragmentTransaction { replace(R.id.questionDetail_fragmentContainer, QuestionDetailFragment().withId()) }
        editing = false
    }

    fun switchToEdit() {
        fragmentTransaction { replace(R.id.questionDetail_fragmentContainer, QuestionFormFragment().withId()) }
        editing = true
    }

    fun Fragment.withId(): Fragment{
        this.arguments = Bundle().apply { putInt("questionId", questionId) }
        return this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> return when(editing){
                true -> {
                    switchToDetail()
                    true
                }
                false -> {
                    navigateUp()
                    true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}


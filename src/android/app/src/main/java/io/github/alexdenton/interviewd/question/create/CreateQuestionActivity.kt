package io.github.alexdenton.interviewd.question.create

import android.os.Bundle
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.QuestionFormFragment
import io.github.rfonzi.rxaware.RxAwareActivity

class CreateQuestionActivity : RxAwareActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null){
            fragmentTransaction { add(R.id.createQuestion_fragmentContainer, QuestionFormFragment()) }
        }

    }
}

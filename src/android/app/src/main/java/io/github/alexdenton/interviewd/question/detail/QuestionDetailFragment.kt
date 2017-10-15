package io.github.alexdenton.interviewd.question.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareFragment


class QuestionDetailFragment : RxAwareFragment() {

    lateinit var vm: QuestionDetailViewModel

    lateinit var nameText: TextView
    lateinit var descText: TextView
    lateinit var estText: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_detail, container, false)
        vm = ViewModelProviders.of(this).get(QuestionDetailViewModel::class.java)
        vm.init(LazyKodein(appKodein))

        if(arguments.containsKey("questionId")){
            vm.getQuestion(arguments.getInt("questionId"))
                    .subscribe ({ setupText(it) },
                            {throwable -> throwable.printStackTrace()})
                    .lifecycleAware()
        }

        nameText = view.findViewById(R.id.questionDetail_name)
        descText = view.findViewById(R.id.questionDetail_description)
        estText = view.findViewById(R.id.questionDetail_estimate)

        setHasOptionsMenu(true)

        return view
    }

    private fun setupText(question: Question) {
        nameText.text = question.name
        descText.text = question.description
        estText.text = resources.getString(R.string.est, question.timeEstimate)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_edit -> postToCurrentActivity(QuestionDetailRouter.EDIT)
        }

        return super.onOptionsItemSelected(item)
    }

}

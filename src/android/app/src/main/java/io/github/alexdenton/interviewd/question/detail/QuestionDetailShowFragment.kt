package io.github.alexdenton.interviewd.question.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.TextView

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareFragment


class QuestionDetailShowFragment : RxAwareFragment() {

    lateinit var vm: QuestionDetailViewModel

    lateinit var nameText: TextView
    lateinit var descText: TextView
    lateinit var estText: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_question_detail_show, container, false)
        vm = ViewModelProviders.of(activity).get(QuestionDetailViewModel::class.java)
        (activity as QuestionDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nameText = view.findViewById(R.id.questionDetail_name)
        descText = view.findViewById(R.id.questionDetail_description)
        estText = view.findViewById(R.id.questionDetail_estimate)

        vm.getQuestionObservable()
                .subscribe { setupText(it) }
                .lifecycleAware()

        setHasOptionsMenu(true)
        retainInstance = true

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
            R.id.menu_edit -> vm.startEditingQuestion()
        }

        return super.onOptionsItemSelected(item)
    }

}

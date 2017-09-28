package io.github.alexdenton.interviewd.conductinterview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class QuestionPageFragment : BaseFragment() {

    lateinit var questionNameText: TextView
    lateinit var questionDescText: TextView
    lateinit var questionEstText: TextView
    lateinit var pageNumber: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_page, container, false)

        val bundle = arguments

        questionNameText = view.findViewById(R.id.questionPage_questionName)
        questionDescText = view.findViewById(R.id.questionPage_questionDescription)
        questionEstText = view.findViewById(R.id.questionPage_questionEstimate)
        pageNumber = view.findViewById(R.id.questionPage_pageNumber)

        questionNameText.text = bundle.getString("name")
        questionDescText.text = bundle.getString("desc")
        questionEstText.text = resources.getString(R.string.est, bundle.getInt("est"))
        pageNumber.text = resources.getString(R.string.question_page_number, bundle.getInt("currentPage"), bundle.getInt("totalPages"))



        return view
    }

}// Required empty public constructor

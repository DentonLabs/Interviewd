package io.github.alexdenton.interviewd.detailquestion


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.textChanges

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class QuestionDetailEditFragment : RxAwareFragment() {

    lateinit var vm: QuestionDetailViewModel

    lateinit var nameEditText: EditText
    lateinit var descEditText: EditText
    lateinit var estEditText: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_detail_edit, container, false)
        vm = ViewModelProviders.of(activity).get(QuestionDetailViewModel::class.java)
        (activity as QuestionDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)


        nameEditText = view.findViewById(R.id.questionDetail_nameEdit)
        descEditText = view.findViewById(R.id.questionDetail_descriptionEdit)
        estEditText = view.findViewById(R.id.questionDetail_estimateEdit)

        vm.getQuestionObservable()
                .subscribe { setupText(it) }

        vm.exposeNameEdits(nameEditText.textChanges())
        vm.exposeDescEdits(descEditText.textChanges())
        vm.exposeEstEdits(estEditText.textChanges())

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_submit_cancel, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> vm.submitEdit()
            R.id.menu_cancel -> vm.cancelEditingQuestion()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupText(question: Question) {
        nameEditText.setText(question.name)
        descEditText.setText(question.description)
        estEditText.setText(question.timeEstimate.toString())
    }

}

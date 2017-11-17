package io.github.alexdenton.interviewd.question


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.question.detail.QuestionDetailRouter
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class QuestionFormFragment : RxAwareFragment() {

    lateinit var vm: QuestionFormViewModel

    lateinit var nameField: EditText
    lateinit var durField: EditText
    lateinit var descField: EditText
    lateinit var submitButton: Button

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_question_form, container, false)
        vm = ViewModelProviders.of(this).get(QuestionFormViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        nameField = view.findViewById(R.id.questionForm_nameField)
        durField = view.findViewById(R.id.questionForm_durationField)
        descField = view.findViewById(R.id.questionForm_descriptionField)
        submitButton = view.findViewById(R.id.questionForm_submitButton)

        if (arguments != null && arguments.containsKey("questionId")) {
            vm.editing = true
            vm.id = arguments.getLong("questionId")
            vm.fetchQuestion()
                    .subscribe {question -> setFields(question)  }
                    .lifecycleAware()
        }

        submitButton.clicks()
                .map { Question(vm.id, nameField.text.toString(), descField.text.toString(), durField.text.toString().toInt()) }
                .flatMap { vm.submitQuestion(it).toObservable() }
                .subscribe ({
                    toast("Submitted Question")
                    if (vm.editing) postToCurrentActivity(QuestionDetailRouter.SHOW) else clearFields()
                },
                        {throwable -> throwable.printStackTrace()})
                .lifecycleAware()

        return view
    }

    fun setFields(question: Question) {
        nameField.setText(question.name)
        durField.setText(question.timeEstimate.toString())
        descField.setText(question.description)
    }

    fun clearFields(){
        nameField.setText("")
        durField.setText("")
        descField.setText("")
    }

}

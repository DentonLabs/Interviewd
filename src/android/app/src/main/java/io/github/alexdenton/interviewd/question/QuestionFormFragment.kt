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
import com.jakewharton.rxbinding2.widget.textChanges

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Question
import io.github.rfonzi.rxaware.RxAwareFragment
import io.reactivex.Observable


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

        submitButton.clicks()
                .map { Question(0, nameField.text.toString(), descField.text.toString(), durField.text.toString().toInt()) }
                .flatMap { vm.submitQuestion(it).toObservable() }
                .subscribe ({
                    toast("Submitted Question")
                    clearFields()
                },
                        {throwable -> throwable.printStackTrace()})
                .lifecycleAware()

        return view
    }

    fun clearFields(){
        nameField.setText("")
        durField.setText("")
        descField.setText("")
    }

}

package io.github.alexdenton.interviewd.createquestion

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.textChanges
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository
import io.github.rfonzi.rxaware.BaseActivity

class CreateQuestionActivity : BaseActivity() {

    lateinit var vm: CreateQuestionViewModel

    lateinit var nameField: EditText
    lateinit var durField: EditText
    lateinit var descField: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)
        vm = ViewModelProviders.of(this).get(CreateQuestionViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        actionBar?.setDisplayHomeAsUpEnabled(true)

        nameField = findViewById(R.id.createQuestionNameField)
        durField = findViewById(R.id.createQuestionDurationField)
        descField = findViewById(R.id.createQuestionDescField)
        submitButton = findViewById(R.id.createQuestionSubmitButton) // TODO: Use a different naming convention for ids

        vm.exposeNameField(nameField.textChanges().map { it.toString() })
        vm.exposeEstimateField(durField.textChanges().filter { it.isNotBlank() }.map { it.toString().toInt() })
        vm.exposeDescField(descField.textChanges().map { it.toString() })
        vm.exposeSubmitButton(submitButton.clicks())

        vm.clearSignal
                .subscribe {
                    nameField.setText("")
                    durField.setText("")
                    descField.setText("")
                }
                .lifecycleAware()


    }
}

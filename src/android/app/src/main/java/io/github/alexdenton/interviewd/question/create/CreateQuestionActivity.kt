package io.github.alexdenton.interviewd.question.create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.widget.textChanges
import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.RxAwareActivity

class CreateQuestionActivity : RxAwareActivity() {

    lateinit var vm: CreateQuestionViewModel

    lateinit var nameField: EditText
    lateinit var durField: EditText
    lateinit var descField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)
        vm = ViewModelProviders.of(this).get(CreateQuestionViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        actionBar?.setDisplayHomeAsUpEnabled(true)

        nameField = findViewById(R.id.createQuestionNameField)
        durField = findViewById(R.id.createQuestionDurationField)
        descField = findViewById(R.id.createQuestionDescField)

        vm.exposeNameField(nameField.textChanges().map { it.toString() })
        vm.exposeEstimateField(durField.textChanges().filter { it.isNotBlank() }.map { it.toString().toInt() })
        vm.exposeDescField(descField.textChanges().map { it.toString() })

        vm.clearSignal
                .subscribe {
                    nameField.setText("")
                    durField.setText("")
                    descField.setText("")
                }
                .lifecycleAware()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_submit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> vm.submitQuestion()
        }

        return super.onOptionsItemSelected(item)
    }
}

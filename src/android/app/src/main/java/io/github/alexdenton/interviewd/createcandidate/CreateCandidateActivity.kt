package io.github.alexdenton.interviewd.createcandidate

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.BaseActivity

class CreateCandidateActivity : BaseActivity() {

    lateinit var vm: CreateCandidateViewModel

    lateinit var firstNameField: EditText
    lateinit var lastNameField: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(CreateCandidateViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        firstNameField = findViewById(R.id.createCandidate_firstNameField)
        lastNameField = findViewById(R.id.createCandidate_lastNameField)
        submitButton = findViewById(R.id.createCandidate_submitButton)

        vm.exposeFirstNameField(firstNameField.textChanges())
        vm.exposeLastNameField(lastNameField.textChanges())
        vm.exposeSubmitButton(submitButton.clicks())
    }
}

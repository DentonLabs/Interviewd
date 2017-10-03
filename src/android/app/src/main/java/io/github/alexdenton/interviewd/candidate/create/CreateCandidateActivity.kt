package io.github.alexdenton.interviewd.candidate.create

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

class CreateCandidateActivity : RxAwareActivity() {

    lateinit var vm: CreateCandidateViewModel

    lateinit var firstNameField: EditText
    lateinit var lastNameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(CreateCandidateViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        firstNameField = findViewById(R.id.createCandidate_firstNameField)
        lastNameField = findViewById(R.id.createCandidate_lastNameField)

        vm.exposeFirstNameField(firstNameField.textChanges())
        vm.exposeLastNameField(lastNameField.textChanges())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_submit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> vm.submitCandidate()
        }

        return super.onOptionsItemSelected(item)
    }
}

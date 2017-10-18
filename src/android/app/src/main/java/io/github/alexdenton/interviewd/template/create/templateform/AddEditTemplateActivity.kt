package io.github.alexdenton.interviewd.template.create.templateform

import android.os.Bundle
import android.view.MenuItem
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.template.create.questionbank.QuestionBankFragment
import io.github.rfonzi.rxaware.RxAwareActivity
import io.github.rfonzi.rxaware.bus.RxBus
import io.github.rfonzi.rxaware.bus.events.FlushEvent

class AddEditTemplateActivity : RxAwareActivity() {

    val questionBankFragment = QuestionBankFragment()
    val templateFormFragment = TemplateFormFragment()
    var inQuestionBank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_template)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null){
            fragmentTransaction {
                add(R.id.addEditTemplate_fragmentContainer, templateFormFragment)
            }
        }

        onPost<TemplateFormRouter>()
                .subscribe {
                    when(it) {
                        is ShowTemplateFormFragment -> switchToForm()
                        is ShowQuestionBankFragment -> switchToQuestionBank()
                        is Leave -> leave()
                    }
                }
                .lifecycleAware()

    }


    fun switchToForm(){
        fragmentTransaction { replace(R.id.addEditTemplate_fragmentContainer, templateFormFragment) }
        inQuestionBank = false
    }

    fun switchToQuestionBank(){
        fragmentTransaction { replace(R.id.addEditTemplate_fragmentContainer, questionBankFragment) }
        inQuestionBank = true
    }

    fun leave(){
        RxBus.post(FlushEvent())
        onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> return when(inQuestionBank){
                true -> {
                    switchToForm()
                    true
                }
                false -> {
                    leave()
                    true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}

package io.github.alexdenton.interviewd.template.create

import android.os.Bundle
import android.view.MenuItem
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.template.create.events.ShowQuestionBankFragment
import io.github.alexdenton.interviewd.template.create.events.ShowTemplateFormFragment
import io.github.alexdenton.interviewd.template.create.events.TemplateFormRouter
import io.github.alexdenton.interviewd.template.create.questionbank.QuestionBankFragment
import io.github.alexdenton.interviewd.template.create.templateform.TemplateFormFragment
import io.github.rfonzi.rxaware.RxAwareActivity
import io.github.rfonzi.rxaware.bus.RxBus
import io.github.rfonzi.rxaware.bus.events.FlushEvent

class CreateTemplateActivity : RxAwareActivity() {

    val questionBankFragment = QuestionBankFragment()
    val templateFormFragment = TemplateFormFragment()
    var inQuestionBank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_template)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null){
            fragmentTransaction {
                add(R.id.createTemplate_fragmentContainer, templateFormFragment)
            }
        }

        onPost<TemplateFormRouter>()
                .subscribe {
                    when(it) {
                        is ShowTemplateFormFragment -> switchToForm()
                        is ShowQuestionBankFragment -> switchToQuestionBank()
                    }
                }
                .lifecycleAware()

    }


    fun switchToForm(){
        fragmentTransaction { replace(R.id.createTemplate_fragmentContainer, templateFormFragment) }
        inQuestionBank = false
    }

    fun switchToQuestionBank(){
        fragmentTransaction { replace(R.id.createTemplate_fragmentContainer, questionBankFragment) }
        inQuestionBank = true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> return when(inQuestionBank){
                true -> {
                    switchToForm()
                    true
                }
                false -> {
                    navigateUp()
                    RxBus.post(FlushEvent())
                    true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}

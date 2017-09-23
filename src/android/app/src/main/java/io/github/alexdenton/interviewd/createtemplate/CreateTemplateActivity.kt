package io.github.alexdenton.interviewd.createtemplate

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.rfonzi.rxaware.bus.RxBus
import io.github.rfonzi.rxaware.bus.events.FlushEvent
import io.github.alexdenton.interviewd.createtemplate.questionbank.QuestionBankFragment
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormFragment
import io.github.rfonzi.rxaware.BaseActivity

class CreateTemplateActivity : BaseActivity() {

    val templateFormFragment = TemplateFormFragment()
    val questionBankFragment = QuestionBankFragment()

    lateinit var vm: CreateTemplateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_template)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        RxBus.toObservable(SwitchToQuestionBankEvent::class.java)
//                .subscribe({ switchToQuestionBank() },
//                        { throwable -> throwable.printStackTrace() }).lifecycleAware()

        vm = ViewModelProviders.of(this).get(CreateTemplateViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        fragmentTransaction {
            add(R.id.createTemplate_fragmentContainer, templateFormFragment)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0)
                    supportFragmentManager.popBackStack()
                else {
                    leave()
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun leave() {
        NavUtils.navigateUpFromSameTask(this)
        RxBus.post(FlushEvent())
        finish()
    }


}

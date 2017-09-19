package io.github.alexdenton.interviewd.createtemplate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.FlushEvent
import io.github.alexdenton.interviewd.bus.events.NavigateUpEvent
import io.github.alexdenton.interviewd.bus.events.SwitchToQuestionBankEvent
import io.github.alexdenton.interviewd.createtemplate.questionbank.QuestionBankFragment
import io.github.alexdenton.interviewd.createtemplate.templateform.TemplateFormFragment

class CreateTemplateActivity : AppCompatActivity() {

    val templateFormFragment = TemplateFormFragment()
    val questionBankFragment = QuestionBankFragment()

    val navigateUpDisposable = RxBus.toObservable(NavigateUpEvent::class.java)
            .subscribe({ leave() },
                    { throwable -> throwable.printStackTrace() })

    val addQuestionDisposable = RxBus.toObservable(SwitchToQuestionBankEvent::class.java)
            .subscribe({ switchToQuestionBank() },
                    { throwable -> throwable.printStackTrace() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_template)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addFragment(templateFormFragment)

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
        addQuestionDisposable.dispose()
        navigateUpDisposable.dispose()
        RxBus.post(FlushEvent())
        finish()
    }

    infix fun test(value: Int) = value

    private fun switchToQuestionBank() {
        supportFragmentManager.inTransaction {
            replace(R.id.createTemplate_fragmentContainer, questionBankFragment)
            addToBackStack(null)
        }
    }

    fun addFragment(fragment: Fragment)
            = supportFragmentManager.inTransaction { add(R.id.createTemplate_fragmentContainer, fragment) }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

}

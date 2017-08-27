package io.github.alexdenton.interviewd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository
import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.bus.RxBus
import io.github.alexdenton.interviewd.bus.events.SendToCreateTemplateEvent
import io.github.alexdenton.interviewd.bus.events.SendToQuestionBankEvent

class QuestionBankActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var presenter: QuestionBankPresenter
    val numRows = 2

    var questionBank: MutableList<Question> = mutableListOf()
    var alreadyChecked: List<Question> = listOf()

    val adapter = QuestionBankAdapter(questionBank)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_bank)

        presenter = QuestionBankPresenter(QuestionRetrofitRepository(this), this)

        recyclerView = findViewById(R.id.questionBank_recyclerView)

//        adapter.getPositionClicked()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe{ pos -> }

        recyclerView.layoutManager = GridLayoutManager(this, numRows)
        recyclerView.adapter = adapter

        presenter.getCheckedQuestions()
        adapter.setCheckedQuestions(alreadyChecked)
        presenter.getAllQuestions()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        RxBus.post(SendToCreateTemplateEvent(adapter.checkedQuestions))
        presenter.disposeAll()
        super.onDestroy()
    }


    fun setUpQuestions(list: List<Question>) {
        questionBank.addAll(list)
        adapter.notifyDataSetChanged()
    }

    fun onCouldNotConnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
